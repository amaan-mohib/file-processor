import { Outlet, createRootRouteWithContext } from "@tanstack/react-router";
import { TanStackRouterDevtoolsPanel } from "@tanstack/react-router-devtools";
import { TanstackDevtools } from "@tanstack/react-devtools";
import type { IUser } from "@/lib/types";

interface AuthState {
  isAuthenticated: boolean;
  user: IUser | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
}

interface IRouterContext {
  auth: AuthState;
}

export const Route = createRootRouteWithContext<IRouterContext>()({
  component: () => (
    <>
      <Outlet />
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
    </>
  ),
});
