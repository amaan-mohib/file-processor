import { create } from "zustand";
import type { IAuth, IJob } from "../types";
import cloneDeep from "lodash.clonedeep";
import { recentJobs } from "../api/dashboard";

interface IStore {
  auth: IAuth;
  dashboard: {
    upload: {
      files: Record<string, File[]>;
      setFiles: (arg: Record<string, File[]>) => void;
    };
    resetDashboard: () => void;
    query: string;
    setQuery: (value: string) => void;
    isLoading: boolean;
    setIsLoading: (value: boolean) => void;
  };
  breadcrumbs: { name: string; link: string }[];
  recentJobs: {
    data: IJob[];
    isLoading: boolean;
    getRecentJobs: () => Promise<void>;
  };
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
  dashboard: {
    upload: {
      files: {
        CSV: [],
        JSON: [],
        XML: [],
      },
      setFiles(arg) {
        const dashboard = get().dashboard;
        dashboard.upload.files = {
          ...dashboard.upload.files,
          ...arg,
        };
        set({
          dashboard: cloneDeep(dashboard),
        });
      },
    },
    resetDashboard: () => {
      const dashboard = get().dashboard;
      dashboard.upload.files = {
        CSV: [],
        JSON: [],
        XML: [],
      };
      dashboard.query = "";
      set({
        dashboard: cloneDeep(dashboard),
      });
    },
    query: "",
    setQuery(value) {
      const dashboard = get().dashboard;
      dashboard.query = value;
      set({ dashboard: cloneDeep(dashboard) });
    },
    isLoading: false,
    setIsLoading(value) {
      const dashboard = get().dashboard;
      dashboard.isLoading = value;
      set({ dashboard: cloneDeep(dashboard) });
    },
  },
  breadcrumbs: [{ name: "Home", link: "/dashboard" }],
  recentJobs: {
    data: [],
    isLoading: true,
    async getRecentJobs() {
      try {
        const res = get().recentJobs;
        res.isLoading = true;
        set({
          recentJobs: cloneDeep(res),
        });

        const data = await recentJobs();
        res.isLoading = false;
        res.data = data;

        set({
          recentJobs: cloneDeep(res),
        });
      } catch (error) {
        console.error(error);
      }
    },
  },
}));

export default useStore;
