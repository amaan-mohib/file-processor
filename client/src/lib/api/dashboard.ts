import api from ".";
import type { IJob } from "../types";

interface IUploadAndRunRequest {
  files: Record<string, File[]>;
  query: string;
}
export const uploadAndRun = async ({ files, query }: IUploadAndRunRequest) => {
  const filesList: { file: File; type: string }[] = [];
  Object.entries(files).forEach(([type, files]) => {
    files.forEach((file) => {
      filesList.push({
        type,
        file,
      });
    });
  });
  if (filesList.length === 0 || !query) {
    return Promise.reject(new Error("Files and query should be present"));
  }
  const formData = new FormData();
  formData.set("query", query);
  filesList.forEach((value) => {
    formData.append(`files`, value.file);
    formData.append(`fileTypes`, value.type);
  });

  await api.post("/job/create-run", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
};

export const recentJobs = async () => {
  const {
    data: { data },
  } = await api.get<{ data: IJob[] }>("/job/recent");
  return data;
};
