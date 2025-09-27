import { goto } from "$app/navigation";
import { authToken } from "$lib/auth";
import type { LayoutLoad } from "../$types";

export const ssr = false;

export const load: LayoutLoad = async () => {
  if (!authToken().token) {
    goto("/login");
  }
};
