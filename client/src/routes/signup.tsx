import { SignupForm } from "@/components/signup-form";
import { ModeToggle } from "@/components/theme-toggle";
import { createFileRoute, redirect } from "@tanstack/react-router";
import { z } from "zod";

export const Route = createFileRoute("/signup")({
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
  head: () => {
    return {
      meta: [
        {
          title: "Signup - Delta Processor",
        },
      ],
    };
  },
});

function RouteComponent() {
  return (
    <div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
      <div className="fixed left-4 top-4 flex gap-2 items-center">
        <img
          className="size-8 rounded-md pointer-events-none"
          src="/web-app-manifest-512x512.png"
          alt="Delta Processor"
        />
        Delta Processor
      </div>
      <div className="w-full max-w-sm">
        <SignupForm />
      </div>
      <div className="fixed right-4 top-4">
        <ModeToggle />
      </div>
    </div>
  );
}
