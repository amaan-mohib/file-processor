import { createFileRoute, redirect } from "@tanstack/react-router";

export const Route = createFileRoute("/")({
  beforeLoad: ({ context }) => {
    if (!context.auth.isAuthenticated) {
      throw redirect({
        to: "/login",
        search: {
          redirect: "/",
        },
      });
    } else {
      throw redirect({ to: "/dashboard" });
    }
  },
  component: RouteComponent,
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

function RouteComponent() {
  return <div>Loading...</div>;
}
