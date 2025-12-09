import {
  createFileRoute,
  redirect,
  Outlet,
  Link,
} from "@tanstack/react-router";

import { AppSidebar } from "@/components/app-sidebar";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Separator } from "@/components/ui/separator";
import {
  SidebarInset,
  SidebarProvider,
  SidebarTrigger,
} from "@/components/ui/sidebar";
import { ModeToggle } from "@/components/theme-toggle";
import useStore from "@/lib/store/useStore";
import { Fragment } from "react/jsx-runtime";
import Notifications from "@/components/notifications";
import { Button } from "@/components/ui/button";
import { LogOut } from "lucide-react";
import { useAuth } from "@/hooks/useAuth";

export default function Layout() {
  const breadcrumbs = useStore((state) => state.breadcrumbs);
  const { logout } = useAuth();

  const handleLogout = () => {
    logout();
  };

  return (
    <SidebarProvider>
      <AppSidebar />
      <SidebarInset>
        <header className="flex h-16 shrink-0 items-center justify-between gap-2 transition-[width,height] ease-linear group-has-data-[collapsible=icon]/sidebar-wrapper:h-12">
          <div className="flex items-center gap-2 px-4">
            <SidebarTrigger className="-ml-1" />
            <Separator
              orientation="vertical"
              className="mr-2 data-[orientation=vertical]:h-4"
            />
            <Breadcrumb>
              <BreadcrumbList>
                {breadcrumbs.map((item, index) => (
                  <Fragment key={item.name}>
                    {index !== 0 && (
                      <BreadcrumbSeparator
                        key={index}
                        className="hidden md:block"
                      />
                    )}
                    <BreadcrumbItem className="hidden md:block">
                      {index === breadcrumbs.length - 1 ? (
                        <BreadcrumbPage>{item.name}</BreadcrumbPage>
                      ) : (
                        <Link
                          className="hover:text-foreground transition-colors"
                          to={item.link}>
                          {item.name}
                        </Link>
                      )}
                    </BreadcrumbItem>
                  </Fragment>
                ))}
              </BreadcrumbList>
            </Breadcrumb>
          </div>
          <div className="flex gap-2 px-4">
            <Button variant={"outline"} onClick={handleLogout}>
              <LogOut />
              Logout
            </Button>
            <Notifications />
            <ModeToggle />
          </div>
        </header>
        <div className="flex flex-1 flex-col gap-4 p-4 pt-0">
          <Outlet />
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}

export const Route = createFileRoute("/_authenticated")({
  beforeLoad: ({ context, location }) => {
    if (!context.auth.isAuthenticated) {
      throw redirect({
        to: "/login",
        search: {
          // Save current location for redirect after login
          redirect: location.href,
        },
      });
    }
  },
  component: () => <Layout />,
});
