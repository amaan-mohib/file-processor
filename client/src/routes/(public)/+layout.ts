import { authService, authToken } from "$lib/auth";
import type { LayoutLoad } from "./$types";

export const ssr = false;

export const load: LayoutLoad = async () => {
  if (authToken().token) {
    try {
      const user = await authService.getProfile();
      return { user };
    } catch (error) {
      authToken().clear();
    }
  }
};
