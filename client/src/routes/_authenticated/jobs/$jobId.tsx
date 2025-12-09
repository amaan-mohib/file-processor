import JobCard from "@/components/dashboard/job-card";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { getJob } from "@/lib/api/jobs";
import useStore from "@/lib/store/useStore";
import type { IFile, IJob } from "@/lib/types";
import { formatFullDate } from "@/lib/utils";
import { createFileRoute } from "@tanstack/react-router";
import { Upload } from "lucide-react";
import { useEffect, useState } from "react";

export const Route = createFileRoute("/_authenticated/jobs/$jobId")({
  component: RouteComponent,
  head: (ctx) => {
    return {
      meta: [
        {
          title: `Job ${ctx.params.jobId} - Delta Processor`,
        },
      ],
    };
  },
});

function RouteComponent() {
  const { jobId } = Route.useParams();
  const [data, setData] = useState<{ file: IFile; job: IJob } | null>(null);

  useEffect(() => {
    getJob(jobId).then((job) => {
      setData(job);
      useStore.setState({
        breadcrumbs: [
          { name: "Jobs", link: "/jobs" },
          { name: job.job.jobKey, link: `/jobs/${job.job.jobKey}` },
        ],
      });
    });
  }, [jobId]);

  const exportQuery = () => {
    if (!data?.job || !data?.job.query) {
      return;
    }
    const blob = new Blob([data.job.query], { type: "text/plain" });
    const elem = window.document.createElement("a");
    elem.href = window.URL.createObjectURL(blob);
    elem.download = `${data.job.jobKey}-query.dpq`;
    document.body.appendChild(elem);
    elem.click();
    document.body.removeChild(elem);
  };

  if (!data) {
    return <div>Loading...</div>;
  }

  const { job, file } = data;

  return (
    <div className="flex flex-col gap-4">
      <JobCard job={{ ...job, fileName: file.fileName }} download file={file} />
      <Card>
        <CardHeader>
          <CardTitle>Query</CardTitle>
          <CardDescription>Set of queries ran on the file</CardDescription>
          <CardAction>
            <Button variant={"secondary"} onClick={exportQuery}>
              <Upload />
              Export
            </Button>
          </CardAction>
        </CardHeader>
        <CardContent>
          <Textarea rows={20} readOnly value={job.query} />
        </CardContent>
      </Card>
      {job.failedReason && (
        <Card>
          <CardHeader>
            <CardTitle>Failed Reason</CardTitle>
          </CardHeader>
          <CardContent>
            <Textarea rows={20} readOnly value={job.failedReason} />
          </CardContent>
        </Card>
      )}
      <Card>
        <CardHeader>
          <CardTitle>Timeline</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col gap-4">
            <div>
              <p className="text-muted-foreground text-sm">Submitted</p>
              <p className="text-sm">{formatFullDate(job.createdAt)}</p>
            </div>
            <div>
              <p className="text-muted-foreground text-sm">Started</p>
              <p className="text-sm">
                {job.startedAt ? formatFullDate(job.startedAt) : "-"}
              </p>
            </div>
            <div>
              <p className="text-muted-foreground text-sm">Completed</p>
              <p className="text-sm">
                {job.completedAt ? formatFullDate(job.completedAt) : "-"}
              </p>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
