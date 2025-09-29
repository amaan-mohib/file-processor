export interface IUser {
  id: number;
  name: string;
  email: string;
  createdAt: string;
}

export interface IAuth {
  user: IUser | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  setAuth: (args: Partial<IAuth>) => void;
  resetAuth: () => void;
}

export type JobStatus = "PENDING" | "IN_PROGRESS" | "COMPLETED" | "FAILED";

export interface IJob {
  jobKey: string;
  query: string;
  status: JobStatus;
  processedSize: Number | null;
  failedReason: string | null;
  createdAt: string;
  updatedAt: string;
  startedAt: string;
  completedAt: string;
}
