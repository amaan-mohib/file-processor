import { create } from "zustand";
import type { IJob } from "../types";
import { recentJobs } from "../api/dashboard";
import { immer } from "zustand/middleware/immer";

interface IStore {
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

const useStore = create<IStore>()(
  immer((set) => ({
    dashboard: {
      upload: {
        files: {
          CSV: [],
          JSON: [],
          XML: [],
        },
        setFiles(arg) {
          set((s) => {
            s.dashboard.upload.files = {
              ...s.dashboard.upload.files,
              ...arg,
            };
          });
        },
      },
      resetDashboard: () => {
        set((s) => {
          s.dashboard.upload.files = {
            CSV: [],
            JSON: [],
            XML: [],
          };
          s.dashboard.query = "";
        });
      },
      query: "",
      setQuery(value) {
        set((s) => {
          s.dashboard.query = value;
        });
      },
      isLoading: false,
      setIsLoading(value) {
        set((s) => {
          s.dashboard.isLoading = value;
        });
      },
    },
    breadcrumbs: [{ name: "Home", link: "/dashboard" }],
    recentJobs: {
      data: [],
      isLoading: true,
      async getRecentJobs() {
        try {
          set((s) => {
            s.recentJobs.isLoading = true;
          });
          const data = await recentJobs();
          set((s) => {
            s.recentJobs.data = data;
            s.recentJobs.isLoading = false;
          });
        } catch (error) {
          console.error(error);
        } finally {
          set((s) => {
            s.recentJobs.isLoading = false;
          });
        }
      },
    },
  }))
);

export default useStore;
