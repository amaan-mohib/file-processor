import { create } from "zustand";
import type { IAuth } from "../types";

const useAuthStore = create<IAuth>((set) => ({
  user: null,
  isAuthenticated: false,
  isLoading: false,
  setAuth(args) {
    set(args);
  },
  resetAuth() {
    set({
      user: null,
      isLoading: false,
      isAuthenticated: false,
    });
  },
}));

export default useAuthStore;
