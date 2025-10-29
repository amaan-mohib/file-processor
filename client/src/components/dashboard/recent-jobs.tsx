import React, { useEffect } from "react";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "../ui/card";
import { Link } from "@tanstack/react-router";
import { Button } from "../ui/button";
import { ArrowRight, RefreshCcw } from "lucide-react";
import useStore from "@/lib/store/useStore";
import JobCard from "./job-card";
import { Tooltip, TooltipContent, TooltipTrigger } from "../ui/tooltip";

interface RecentJobsProps {}

const RecentJobs: React.FC<RecentJobsProps> = () => {
  const { data, isLoading, getRecentJobs } = useStore(
    (state) => state.recentJobs
  );

  useEffect(() => {
    getRecentJobs();
  }, []);

  return (
    <Card className="flex-1">
      <CardHeader>
        <CardTitle>Recent Jobs</CardTitle>
        <CardDescription>
          <Link to="/jobs">
            <Button
              variant={"link"}
              size={"sm"}
              className="ml-[-10px] cursor-pointer">
              <span>View all</span>
              <ArrowRight />
            </Button>
          </Link>
        </CardDescription>
        <CardAction>
          <Tooltip>
            <TooltipTrigger>
              <Button
                variant={"secondary"}
                size={"icon"}
                disabled={isLoading}
                onClick={() => {
                  getRecentJobs();
                }}>
                <RefreshCcw />
              </Button>
            </TooltipTrigger>
            <TooltipContent side="left">Refresh</TooltipContent>
          </Tooltip>
        </CardAction>
      </CardHeader>
      <CardContent className="flex-1 max-h-[calc(100vh-13rem)] overflow-auto">
        {data.length === 0 ? (
          <p className="text-muted-foreground text-sm">No jobs ran</p>
        ) : (
          <div className="flex flex-col gap-4">
            {data.map((item) => (
              <JobCard key={item.jobKey} job={item} file={item.file} />
            ))}
          </div>
        )}
      </CardContent>
    </Card>
  );
};

export default RecentJobs;
