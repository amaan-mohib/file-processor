import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Link, useRouter, useRouterState } from "@tanstack/react-router";
import { useState } from "react";
import { Route } from "@/routes/signup";
import z from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "./ui/form";
import { useAuth } from "@/hooks/useAuth";

const formSchema = z.object({
  name: z
    .string()
    .min(2, { error: "Name should be at least 2 characters long" })
    .max(100, { error: "Name should be at most 100 characters long" }),
  email: z.email(),
  password: z
    .string()
    .min(6, { error: "Password should be at least 6 characters long" })
    .max(100, { error: "Password should be at most 100 characters long" }),
});

export function SignupForm({
  className,
  ...props
}: React.ComponentProps<"div">) {
  const { register } = useAuth();
  const router = useRouter();
  const isLoading = useRouterState({ select: (s) => s.isLoading });
  const navigate = Route.useNavigate();
  const search = Route.useSearch();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const isLoggingIn = isLoading || isSubmitting;

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
      email: "",
      password: "",
    },
  });

  async function onSubmit(values: z.infer<typeof formSchema>) {
    setIsSubmitting(true);
    try {
      setError(null);
      await register(values.name, values.email, values.password);
      await router.invalidate();
      await navigate({ to: "/login", search: { redirect: search.redirect } });
    } catch (error: any) {
      const message = error.response?.data?.message || "Something went wrong";
      const errors = error.response?.data?.errors || [];
      setError(`${message}: ${errors.join()}`);
      console.error(error);
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle>Register your account</CardTitle>
          <CardDescription>Enter the details below to register</CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)}>
              <div className="flex flex-col gap-6">
                <div className="grid gap-3">
                  <FormField
                    control={form.control}
                    name="name"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Name</FormLabel>
                        <FormControl>
                          <Input placeholder="John Doe" required {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                <div className="grid gap-3">
                  <FormField
                    control={form.control}
                    name="email"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Email</FormLabel>
                        <FormControl>
                          <Input
                            type="email"
                            placeholder="m@example.com"
                            required
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                <div className="grid gap-3">
                  <FormField
                    control={form.control}
                    name="password"
                    render={({ field }) => (
                      <FormItem>
                        <div className="flex items-center">
                          <FormLabel htmlFor="password">Password</FormLabel>
                        </div>
                        <FormControl>
                          <Input type="password" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                {error && <div className="text-sm text-red-600">{error}</div>}
                <div className="flex flex-col gap-3">
                  <Button
                    type="submit"
                    className="w-full"
                    disabled={isLoggingIn}>
                    Signup
                  </Button>
                </div>
              </div>
              <div className="mt-4 text-center text-sm">
                Already have an account?{" "}
                <Link to="/login" className="underline underline-offset-4">
                  Login
                </Link>
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}
