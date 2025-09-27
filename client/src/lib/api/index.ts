import axios from "axios";

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
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      // Handle token refresh logic here
      // For example, make a request to a refresh token endpoint
      // const newAccessToken = await refreshToken();
      // localStorage.setItem('authToken', newAccessToken);
      // originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
      // return api(originalRequest); // Retry the original request
    }
    return Promise.reject(error);
  }
);

export default api;
