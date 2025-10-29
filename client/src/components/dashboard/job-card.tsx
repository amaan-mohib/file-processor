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
import { getInputFile } from "@/lib/api/files";
import { toast } from "sonner";

interface JobCardProps {
  job: IJob;
  download?: boolean;
  file?: IFile;
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

const JobCard: React.FC<JobCardProps> = ({ job, download, file }) => {
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
        <CardTitle>{job.jobKey}</CardTitle>
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
                getOutputFile(job.jobKey).then((data) => {
                  const blob = new Blob([data], {
                    type:
                      file.fileType === "CSV"
                        ? "text/csv"
                        : file.fileType === "JSON"
                          ? "application/json"
                          : "application/xml",
                  });
                  const url = window.URL.createObjectURL(blob);
                  const a = document.createElement("a");
                  document.body.appendChild(a);
                  a.style = "display: none";
                  a.href = url;
                  a.download = `${job.fileName.split(".")[0]}_output.${file.fileType.toLowerCase()}`;
                  a.click();
                  window.URL.revokeObjectURL(url);
                });
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
      </CardHeader>
      <CardContent>
        <div className="flex items-center">
          <p className="text-muted-foreground text-sm">File:</p>
          <Button
            onClick={() => {
              if (!file) return;
              getInputFile(file?.fileKey).then((data) => {
                const blob = new Blob([data], {
                  type:
                    file.fileType === "CSV"
                      ? "text/csv"
                      : file.fileType === "JSON"
                        ? "application/json"
                        : "application/xml",
                });
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement("a");
                document.body.appendChild(a);
                a.style = "display: none";
                a.href = url;
                a.download = file.fileName;
                a.click();
                window.URL.revokeObjectURL(url);
              });
            }}
            className="cursor-pointer h-fit ml-[-8px]"
            size={"sm"}
            variant={"link"}>
            {file?.fileName}
          </Button>
        </div>
        <p className="text-muted-foreground text-sm">
          {job.status === "COMPLETED" && job.completedAt && (
            <>Completed at: {formatFullDate(job.completedAt)}</>
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
