import axios from "axios";
import { toast } from "sonner";
import { AUTH_TOKEN_KEY, authService, REFRESH_TOKEN_KEY } from "../auth";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10_000,
});

let isRefreshing = false;
let failedQueue: any[] = [];

const processQueue = (error: any, token: string | null) => {
  failedQueue.forEach((prom) => {
    if (error) prom.reject(error);
    else prom.resolve(token || null);
  });
  failedQueue = [];
};

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("authToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;
    if ([401, 403].includes(error.response.status) && !originalRequest._retry) {
      originalRequest._retry = true;

      if (isRefreshing) {
        // Queue the request until refresh is done
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        }).then((token) => {
          originalRequest.headers.Authorization = `Bearer ${token}`;
          return api(originalRequest);
        });
      }

      isRefreshing = true;

      try {
        const { refreshToken } = await authService.refresh();
        api.defaults.headers.Authorization = `Bearer ${refreshToken}`;
        processQueue(null, refreshToken);

        // Retry the original request
        originalRequest.headers.Authorization = `Bearer ${refreshToken}`;
        return api(originalRequest);
      } catch (err) {
        toast.error("Unauthorized");
        processQueue(err, null);
        localStorage.removeItem(AUTH_TOKEN_KEY);
        localStorage.removeItem(REFRESH_TOKEN_KEY);
        window.location.href = `/login?redirect=${window.location.pathname}`;
        return Promise.reject(err);
      } finally {
        isRefreshing = false;
      }
    } else if (error.response.status === 500) {
      toast.error(error.response.message || "Something went wrong");
    }
    return Promise.reject(error);
  }
);

export default api;
