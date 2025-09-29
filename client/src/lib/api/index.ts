import { router } from "@/main";
import axios from "axios";
import { toast } from "sonner";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 10_000,
});

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
      toast.error("Unauthorized");
      originalRequest._retry = true;
      router.navigate({
        to: "/login",
        search: { redirect: window.location.href || "" },
      });
      // Handle token refresh logic here
      // For example, make a request to a refresh token endpoint
      // const newAccessToken = await refreshToken();
      // localStorage.setItem('authToken', newAccessToken);
      // originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
      // return api(originalRequest); // Retry the original request
    } else if (error.response.status === 500) {
      toast.error(error.response.message || "Something went wrong");
    }
    return Promise.reject(error);
  }
);

export default api;
