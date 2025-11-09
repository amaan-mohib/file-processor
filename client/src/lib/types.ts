export interface IUser {
  id: number;
  name: string;
  email: string;
  createdAt: string;
}

export interface ILoginResponse {
  token: string;
  refreshToken: string;
  expiresIn: number;
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
  fileName: string;
  processedSize: Number | null;
  failedReason: string | null;
  createdAt: string;
  updatedAt: string | null;
  startedAt: string | null;
  completedAt: string | null;
  file?: IFile;
}

export interface IFile {
  fileKey: string;
  fileName: string;
  fileType: "CSV" | "JSON" | "XML";
  originalSize: number;
  createdAt: string;
}

export interface IPage {
  offset?: number;
  pageSize?: number;
  sortDirection?: string;
  sortBy?: string;
}
