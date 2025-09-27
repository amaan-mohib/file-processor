import { LoginForm } from "@/components/login-form";
import { ModeToggle } from "@/components/theme-toggle";
import { createFileRoute, redirect } from "@tanstack/react-router";
import { z } from "zod";

export const Route = createFileRoute("/login")({
  validateSearch: z.object({
    redirect: z.string().optional().catch(""),
  }),
  beforeLoad: ({ context, search }) => {
    // Redirect if already authenticated
    if (context.auth.isAuthenticated) {
      throw redirect({ to: search.redirect || "/dashboard" });
    }
  },
  component: RouteComponent,
});

function RouteComponent() {
  return (
    <div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
      <div className="fixed left-4 top-4">Delta Processor</div>
      <div className="w-full max-w-sm">
        <LoginForm />
      </div>
      <div className="fixed right-4 top-4">
        <ModeToggle />
      </div>
    </div>
  );
}
