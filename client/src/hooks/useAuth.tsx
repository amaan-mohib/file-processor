import { authService, authToken } from "../lib/auth";
import useStore from "../lib/store/useStore";

export const useAuth = () => {
  const { user, isAuthenticated, isLoading, setAuth, resetAuth } = useStore(
    (state) => state.auth
  );

  const getUser = async () => {
    try {
      setAuth({ isLoading: true });
      const user = await authService.getProfile();
      setAuth({ user, isAuthenticated: true });
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
    await authService.login(email, password).catch(console.error);
    await getUser();
  };

  const logout = async () => {
    await authService.logout();
    resetAuth();
  };

  return { user, isAuthenticated, isLoading, login, logout, init };
};
