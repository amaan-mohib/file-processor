import { create } from "zustand";
import type { IUser } from "../types";

interface IAuth {
  user: IUser | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  setAuth: (args: Partial<IAuth>) => void;
  resetAuth: () => void;
}
interface IStore {
  auth: IAuth;
}

const useStore = create<IStore>((set, get) => ({
  auth: {
    user: null,
    isAuthenticated: false,
    isLoading: false,
    setAuth(args) {
      set({
        auth: {
          ...get().auth,
          ...args,
        },
      });
    },
    resetAuth() {
      set({
        auth: {
          ...get().auth,
          user: null,
          isAuthenticated: false,
          isLoading: false,
        },
      });
    },
  },
}));

export default useStore;
