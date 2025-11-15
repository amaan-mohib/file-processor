import api from "./api";
import type { ILoginResponse, IUser } from "./types";

export const AUTH_TOKEN_KEY = "authToken";
export const REFRESH_TOKEN_KEY = "refreshToken";

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
      localStorage.removeItem(REFRESH_TOKEN_KEY);
      window.location.href = `/login?redirect=${window.location.pathname}`;
    },
  };
}

export const authService = {
  login: async (email: string, password: string) => {
    const { data } = await api.post<ILoginResponse>("/auth/login", {
      email,
      password,
    });
    localStorage.setItem(AUTH_TOKEN_KEY, data.token);
    localStorage.setItem(REFRESH_TOKEN_KEY, data.refreshToken);
    return data;
  },
  logout: async () => {
    const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);
    await api.post("/auth/logout", { refreshToken });
    localStorage.removeItem(REFRESH_TOKEN_KEY);
    localStorage.removeItem(AUTH_TOKEN_KEY);
    window.location.href = "/login";
  },
  register: async (name: string, email: string, password: string) => {
    const response = await api.post("/auth/signup", { name, email, password });
    return response.data;
  },
  refresh: async () => {
    const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);
    localStorage.removeItem(AUTH_TOKEN_KEY);
    if (!refreshToken) throw new Error("No refresh token available");
    const { data } = await api.post<ILoginResponse>("/auth/refresh", {
      refreshToken,
    });
    localStorage.setItem(AUTH_TOKEN_KEY, data.token);
    localStorage.setItem(REFRESH_TOKEN_KEY, data.refreshToken);
    return data;
  },
  getProfile: async () => {
    const {
      data: { data: user },
    } = await api.get<{ data: IUser }>("/users/me");
    return user;
  },
  scheduleRefresh: () => {
    const tokenDuration =
      parseInt(import.meta.env.VITE_TOKEN_DURATION) || 600000; // default to 10 minutes
    setTimeout(async () => {
      try {
        await authService.refresh();
        authService.scheduleRefresh();
      } catch (error) {
        console.error("Failed to refresh token:", error);
        authToken().clear();
      }
    }, tokenDuration - 60000); // refresh 1 minute before expiry
  },
};
