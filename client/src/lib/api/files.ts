import api from ".";
import { REFRESH_TOKEN_KEY } from "../auth";
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
  const url = `${import.meta.env.VITE_API_BASE_URL}/file/serve/${fileKey}?accessToken=${localStorage.getItem(REFRESH_TOKEN_KEY)}`;
  window.open(url, "_blank");
};
