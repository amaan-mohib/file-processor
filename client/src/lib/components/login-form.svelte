<script lang="ts">
  import * as Card from "$lib/components/ui/card/index.js";
  import { Input } from "$lib/components/ui/input/index.js";
  import { defaults, superForm } from "sveltekit-superforms";
  import { zod4 } from "sveltekit-superforms/adapters";
  import { z } from "zod/v4";
  import * as Form from "$lib/components/ui/form/index.js";
  import { authService } from "$lib/auth";
  import { goto } from "$app/navigation";

  const formSchema = z.object({
    email: z.email(),
    password: z.string().min(6, "Password must be at least 6 characters long"),
  });
  const form = superForm(defaults(zod4(formSchema)), {
    validators: zod4(formSchema),
    SPA: true,
    async onSubmit({ formData }) {
      try {
        await authService.login(
          formData.get("email") as string,
          formData.get("password") as string
        );
        await goto("/");
      } catch (error: any) {
        return Promise.reject(
          new Error(error.response?.data?.message || error.message)
        );
      }
    },
    clearOnSubmit: "errors-and-message",
    onError({ result }) {
      $message =
        result.error.message || "There was an error submitting the form";
    },
  });
  const { form: formData, enhance, message } = form;
</script>

<Card.Root class="mx-auto w-full max-w-sm">
  <Card.Header>
    <Card.Title class="text-2xl">Login</Card.Title>
    <Card.Description
      >Enter your email below to login to your account</Card.Description
    >
  </Card.Header>
  <Card.Content>
    <form class="grid gap-4" use:enhance>
      <div class="grid gap-2">
        <Form.Field {form} name="email">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>Email</Form.Label>
              <Input
                {...props}
                type="email"
                placeholder="m@example.com"
                required
                bind:value={$formData.email}
              />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>
      </div>
      <div class="grid gap-2">
        <Form.Field {form} name="password">
          <Form.Control>
            {#snippet children({ props })}
              <div class="flex items-center">
                <Form.Label>Password</Form.Label>
                <!-- <a href="##" class="ml-auto inline-block text-sm underline">
									Forgot your password?
								</a> -->
              </div>
              <Input
                {...props}
                type="password"
                required
                bind:value={$formData.password}
              />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>
      </div>
      {#if $message}
        <div class="text-sm text-red-600">{$message}</div>
      {/if}
      <Form.Button type="submit" class="w-full">Login</Form.Button>
      <!-- <Button variant="outline" class="w-full">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
					<path
						d="M12.48 10.92v3.28h7.84c-.24 1.84-.853 3.187-1.787 4.133-1.147 1.147-2.933 2.4-6.053 2.4-4.827 0-8.6-3.893-8.6-8.72s3.773-8.72 8.6-8.72c2.6 0 4.507 1.027 5.907 2.347l2.307-2.307C18.747 1.44 16.133 0 12.48 0 5.867 0 .307 5.387.307 12s5.56 12 12.173 12c3.573 0 6.267-1.173 8.373-3.36 2.16-2.16 2.84-5.213 2.84-7.667 0-.76-.053-1.467-.173-2.053H12.48z"
						fill="currentColor"
					/>
				</svg>
				Login with Google
			</Button> -->
    </form>
    <div class="mt-4 text-center text-sm">
      Don't have an account?
      <a href="##" class="underline"> Sign up </a>
    </div>
  </Card.Content>
</Card.Root>
