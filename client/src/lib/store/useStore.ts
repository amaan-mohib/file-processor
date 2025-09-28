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
  upload: {
    files: Record<string, File[]>;
    setFiles: (arg: Record<string, File[]>) => void;
  };
  breadcrumbs: { name: string; link: string }[];
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
  upload: {
    files: {
      CSV: [],
      JSON: [],
      XML: [],
    },
    setFiles(arg) {
      set({
        upload: {
          ...get().upload,
          files: {
            ...get().upload.files,
            ...arg,
          },
        },
      });
    },
  },
  breadcrumbs: [{ name: "Home", link: "/dashboard" }],
}));

export default useStore;
