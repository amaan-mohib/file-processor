import {
  HeadContent,
  Outlet,
  createRootRouteWithContext,
} from "@tanstack/react-router";
import { TanStackRouterDevtoolsPanel } from "@tanstack/react-router-devtools";
import { TanstackDevtools } from "@tanstack/react-devtools";
import type { IUser } from "@/lib/types";
import { Toaster } from "@/components/ui/sonner";

interface AuthState {
  isAuthenticated: boolean;
  user: IUser | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
  init: () => Promise<void>;
}

interface IRouterContext {
  auth: AuthState;
}

export const Route = createRootRouteWithContext<IRouterContext>()({
  component: () => (
    <>
      <HeadContent />
      <Outlet />
      <Toaster />
      {import.meta.env.NODE_ENV !== "production" && (
        <TanstackDevtools
          config={{
            position: "bottom-left",
          }}
          plugins={[
            {
              name: "Tanstack Router",
              render: <TanStackRouterDevtoolsPanel />,
            },
          ]}
        />
      )}
    </>
  ),
  head: () => {
    return {
      meta: [
        {
          title: "Delta Processor",
        },
      ],
    };
  },
});
