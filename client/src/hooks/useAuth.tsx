import useAuthStore from "@/lib/store/useAuthStore";
import { authService, authToken } from "../lib/auth";

export const useAuth = () => {
  const { user, isAuthenticated, isLoading, setAuth, resetAuth } =
    useAuthStore();

  const getUser = async () => {
    try {
      setAuth({ isLoading: true });
      const user = await authService.getProfile();
      setAuth({ user, isAuthenticated: true });
      authService.scheduleRefresh();
    } catch (error) {
      authToken().clear();
    } finally {
      setAuth({ isLoading: false });
    }
  };

  const init = async () => {
    if (user) return;
    const { token } = authToken();
    if (token) {
      await getUser();
    } else {
      resetAuth();
    }
  };

  const login = async (email: string, password: string) => {
    await authService.login(email, password);
    await getUser();
  };

  const register = async (name: string, email: string, password: string) => {
    await authService.register(name, email, password);
    // await getUser();
  };

  const logout = async () => {
    await authService.logout();
    resetAuth();
  };

  return { user, isAuthenticated, isLoading, login, register, logout, init };
};
