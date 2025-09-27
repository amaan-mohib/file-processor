import api from "./api";
import type { IUser } from "./types";

export function authToken() {
  return {
    get token(): string | null {
      return localStorage.getItem("authToken") || null;
    },
    set token(value: string) {
      localStorage.setItem("authToken", value);
    },
    clear() {
      localStorage.removeItem("authToken");
      window.location.href = "/login";
    },
  };
}

export const authService = {
  login: async (email: string, password: string) => {
    const response = await api.post("/auth/login", { email, password });
    localStorage.setItem("authToken", response.data.token);
    return response.data;
  },
  logout: () => {
    localStorage.removeItem("authToken");
    window.location.href = "/login";
  },
  register: async (name: string, email: string, password: string) => {
    const response = await api.post("/auth/signup", { name, email, password });
    return response.data;
  },
  getProfile: async () => {
    const response = await api.get<IUser>("/users/me");
    return response.data;
  },
};
