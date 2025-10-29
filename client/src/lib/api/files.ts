import api from ".";
import type { IFile, IPage } from "../types";

export const getFiles = async ({
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
      content: IFile[];
      page: number;
      size: number;
      totalElements: number;
      totalPages: number;
    };
  }>(`/file/?${search.toString()}`);
  return data;
};

export const getInputFile = async (fileKey: string) => {
  const { data } = await api.get(`/file/${fileKey}`);
  if (typeof data === "string") {
    return data;
  }
  return JSON.stringify(data);
};
