import * as React from "react";
import { Files, Home, Logs } from "lucide-react";

import { NavMain } from "@/components/nav-main";
import { NavUser } from "@/components/nav-user";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarRail,
} from "@/components/ui/sidebar";
import { Link } from "@tanstack/react-router";

const data = {
  navMain: [
    {
      title: "Home",
      url: "/",
      icon: Home,
    },
    {
      title: "Jobs",
      url: "/jobs",
      icon: Logs,
    },
    {
      title: "Files",
      url: "/files",
      icon: Files,
    },
  ],
};

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" asChild>
              <Link to="/">
                <img
                  className="size-8 rounded-md pointer-events-none"
                  src="/web-app-manifest-512x512.png"
                  alt="Delta Processor"
                />
                <div className="flex flex-col gap-0.5 leading-none">
                  <span className="font-medium">Delta Processor</span>
                </div>
              </Link>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
