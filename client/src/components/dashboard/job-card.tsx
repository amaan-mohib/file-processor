import React from "react";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "../ui/card";
import type { IJob } from "@/lib/types";
import { Badge, badgeVariants } from "../ui/badge";
import { Button } from "../ui/button";
import type { VariantProps } from "class-variance-authority";
import { formatFullDate } from "@/lib/utils";

interface JobCardProps {
  job: IJob;
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

const JobCard: React.FC<JobCardProps> = ({ job }) => {
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
        <CardAction>
          <Button
            className="cursor-pointer h-fit mr-[-10px]"
            size={"sm"}
            variant={"link"}>
            View
          </Button>
        </CardAction>
      </CardHeader>
      <CardContent>
        <p className="text-muted-foreground text-sm">File: {job.fileName}</p>
        <p className="text-muted-foreground text-sm">
          {job.status === "COMPLETED" && (
            <>Completed at: {formatFullDate(job.completedAt)}</>
          )}
          {job.status === "IN_PROGRESS" && (
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
