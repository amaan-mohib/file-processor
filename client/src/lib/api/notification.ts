import api from ".";
import type { INotification } from "../types";

export const getUnreadNotifications = async () => {
  const {
    data: { data },
  } = await api.get<{ data: INotification[] }>("/notification/");
  return data;
};

export const markNotificationAsRead = async (notificationId: number) => {
  const {
    data: { data },
  } = await api.post<{ data: INotification[] }>(
    `/notification/read/${notificationId}`
  );
  return data;
};

export const markAllNotificationsAsRead = async () => {
  const {
    data: { data },
  } = await api.post<{ data: INotification[] }>(`/notification/mark-all-read`);
  return data;
};
