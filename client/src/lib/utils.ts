import { clsx, type ClassValue } from "clsx";
import { format } from "date-fns";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export const formatFullDate = (date: Date | string | number) => {
  return format(date, "LLL dd, yyyy 'at' h:mm a");
};
