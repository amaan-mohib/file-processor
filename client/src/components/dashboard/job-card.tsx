import React from "react";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "../ui/card";
import type { IFile, IJob } from "@/lib/types";
import { Badge, badgeVariants } from "../ui/badge";
import { Button } from "../ui/button";
import type { VariantProps } from "class-variance-authority";
import { formatFullDate } from "@/lib/utils";
import { Link } from "@tanstack/react-router";
import { getOutputFile, rerunJob } from "@/lib/api/jobs";
import { toast } from "sonner";

interface JobCardProps {
  job: IJob;
  download?: boolean;
  file?: IFile;
  hideActions?: boolean;
}

export const getStatus = (status: IJob["status"]) => {
  const map: Record<
    IJob["status"],
    {
      text: string;
      variant: VariantProps<typeof badgeVariants>["variant"];
      color?: string;
    }
  > = {
    COMPLETED: {
      text: "Completed",
      variant: "secondary",
      color: "bg-green-500 text-white dark:bg-green-600",
    },
    FAILED: {
      text: "Failed",
      variant: "destructive",
    },
    IN_PROGRESS: {
      text: "In Progress",
      variant: "secondary",
    },
    PENDING: {
      text: "Pending",
      variant: "outline",
    },
  };
  return map[status];
};

const JobCard: React.FC<JobCardProps> = ({
  job,
  download,
  file,
  hideActions,
}) => {
  const status = getStatus(job.status);

  return (
    <Card>
      <CardHeader>
        <Badge
          className={`rounded-full ${status.color ? status.color : ""}`}
          variant={status.variant}>
          {status.text}
        </Badge>
        <CardDescription>Job ID</CardDescription>
        {hideActions ? (
          <Link to={`/jobs/$jobId`} params={{ jobId: job.jobKey }}>
            <CardTitle className="hover:underline">{job.jobKey}</CardTitle>
          </Link>
        ) : (
          <CardTitle>{job.jobKey}</CardTitle>
        )}
        {hideActions ? null : (
          <CardAction className="flex gap-4 items-center">
            {download && job.status === "COMPLETED" && (
              <Button
                onClick={() => {
                  rerunJob(job.jobKey).then(() => {
                    toast.success("Job re-ran successfully");
                  });
                }}
                className="cursor-pointer h-fit"
                variant={"link"}>
                Rerun
              </Button>
            )}
            {download && file ? (
              <Button
                disabled={job.status !== "COMPLETED"}
                onClick={() => {
                  getOutputFile(job.jobKey);
                }}
                size={"sm"}>
                Download
              </Button>
            ) : (
              <Link to={`/jobs/$jobId`} params={{ jobId: job.jobKey }}>
                <Button
                  className="cursor-pointer h-fit mr-[-10px]"
                  size={"sm"}
                  variant={"link"}>
                  View
                </Button>
              </Link>
            )}
          </CardAction>
        )}
      </CardHeader>
      <CardContent>
        {file && (
          <div className="flex items-center">
            <p className="text-muted-foreground text-sm">File:</p>
            <Link to={`/files/$fileId`} params={{ fileId: file.fileKey || "" }}>
              <Button
                className="cursor-pointer h-fit ml-[-8px] "
                size={"sm"}
                variant={"link"}
                title={file.fileName}>
                <span className="max-w-[300px] truncate block">
                  {file.fileName}
                </span>
              </Button>
            </Link>
          </div>
        )}
        <p className="text-muted-foreground text-sm">
          {job.status === "COMPLETED" && job.completedAt && (
            <div>
              <span>Completed at: {formatFullDate(job.completedAt)}</span>
              <br />
              {job.startedAt && (
                <span>
                  Time taken:{" "}
                  {new Date(job.completedAt).getTime() -
                    new Date(job.startedAt).getTime()}
                  ms
                </span>
              )}
            </div>
          )}
          {job.status === "IN_PROGRESS" && job.startedAt && (
            <>Started at: {formatFullDate(job.startedAt)}</>
          )}
          {(job.status === "PENDING" || job.status === "FAILED") && (
            <>Created at: {formatFullDate(job.createdAt)}</>
          )}
        </p>
      </CardContent>
    </Card>
  );
};

export default JobCard;
