import api from "./api";
import type { IUser } from "./types";

const AUTH_TOKEN_KEY = "authToken";

export function authToken() {
  return {
    get token(): string | null {
      return localStorage.getItem(AUTH_TOKEN_KEY) || null;
    },
    set token(value: string) {
      localStorage.setItem(AUTH_TOKEN_KEY, value);
    },
    clear() {
      localStorage.removeItem(AUTH_TOKEN_KEY);
      window.location.href = "/login";
    },
  };
}

export const authService = {
  login: async (email: string, password: string) => {
    const response = await api.post("/auth/login", { email, password });
    localStorage.setItem(AUTH_TOKEN_KEY, response.data.token);
    return response.data;
  },
  logout: async () => {
    localStorage.removeItem(AUTH_TOKEN_KEY);
    window.location.href = "/login";
  },
  register: async (name: string, email: string, password: string) => {
    const response = await api.post("/auth/signup", { name, email, password });
    return response.data;
  },
  getProfile: async () => {
    const {
      data: { data: user },
    } = await api.get<{ data: IUser }>("/users/me");
    return user;
  },
};
