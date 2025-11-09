import api from ".";
import { REFRESH_TOKEN_KEY } from "../auth";
import type { IFile, IJob, IPage } from "../types";

export const getJobs = async ({
  offset = 0,
  pageSize = 25,
  sortDirection,
  sortBy,
}: IPage = {}) => {
  const search = new URLSearchParams();

  if (offset) {
    search.append("offset", offset.toString());
  }
  if (pageSize) {
    search.append("pageSize", pageSize.toString());
  }
  if (sortBy) {
    search.append("sortBy", sortBy);
  }
  if (sortDirection) {
    search.append("sortDirection", sortDirection);
  }

  const {
    data: { data },
  } = await api.get<{
    data: {
      content: IJob[];
      page: number;
      size: number;
      totalElements: number;
      totalPages: number;
    };
  }>(`/job/?${search.toString()}`);
  return data;
};

export const getJob = async (jobKey: string) => {
  const {
    data: { data },
  } = await api.get<{
    data: {
      file: IFile;
      job: IJob;
    };
  }>(`/job/${jobKey}`);
  return data;
};

export const getOutputFile = async (jobKey: string) => {
  const url = `${import.meta.env.VITE_API_BASE_URL}/job/output/${jobKey}?accessToken=${localStorage.getItem(REFRESH_TOKEN_KEY)}`;
  window.open(url, "_blank");
};

export const rerunJob = async (jobKey: string) => {
  const {
    data: { data },
  } = await api.post<{
    data: IJob;
  }>(`/job/rerun/${jobKey}`);
  return data;
};
