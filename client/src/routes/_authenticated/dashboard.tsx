import QueryCard from "@/components/dashboard/query-card";
import RecentJobs from "@/components/dashboard/recent-jobs";
import UploadCard from "@/components/dashboard/upload-card";
import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/_authenticated/dashboard")({
  component: RouteComponent,
});

function RouteComponent() {
  return (
    <div className="flex flex-1 flex-col gap-4 xl:flex-row">
      <div className="flex w-full flex-col gap-4">
        <UploadCard />
        <QueryCard />
      </div>
      <div className="w-full h-full flex xl:max-w-md xl:sticky xl:top-16 self-start">
        <RecentJobs />
      </div>
    </div>
  );
}
