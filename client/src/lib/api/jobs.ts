import api from ".";
import type { IJob, IPage } from "../types";

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
