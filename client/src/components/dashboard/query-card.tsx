import React, { useMemo } from "react";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "../ui/card";
import { Textarea } from "../ui/textarea";
import { Button } from "../ui/button";
import useStore from "@/lib/store/useStore";
import { uploadAndRun } from "@/lib/api/dashboard";
import { toast } from "sonner";
import { HelpCircle } from "lucide-react";
import { Tooltip, TooltipContent, TooltipTrigger } from "../ui/tooltip";

interface QueryCardProps {}

const QueryCard: React.FC<QueryCardProps> = () => {
  const {
    query,
    setQuery,
    upload: { files },
    resetDashboard,
    isLoading,
    setIsLoading,
  } = useStore((state) => state.dashboard);
  const getRecentJobs = useStore((state) => state.recentJobs.getRecentJobs);

  const fileLen = useMemo(() => {
    let len = 0;
    Object.values(files).forEach((files) => {
      len += files.length;
    });
    return len;
  }, [files]);

  const handleUpload = async () => {
    try {
      setIsLoading(true);
      await uploadAndRun({ files, query });
      resetDashboard();
      toast.success("Job started");
      setIsLoading(false);
      await getRecentJobs();
    } catch (error: any) {
      console.log(error);
      toast.error(error.response?.data?.message || "Something went wrong");
      setIsLoading(false);
    }
  };

  return (
    <Card className="flex-1">
      <CardHeader>
        <CardTitle>Query</CardTitle>
        <CardDescription>
          Queries that need to be performed on the uploaded assets
        </CardDescription>
        <CardAction>
          <Tooltip>
            <TooltipTrigger>
              <a
                href="https://github.com/amaan-mohib/file-processor/blob/main/FILEQUERY_SYNTAX.md"
                target="_blank">
                <Button
                  variant={"secondary"}
                  size={"icon"}
                  className="cursor-pointer">
                  <HelpCircle />
                </Button>
              </a>
            </TooltipTrigger>
            <TooltipContent side="left">Help</TooltipContent>
          </Tooltip>
        </CardAction>
      </CardHeader>
      <CardContent className="flex flex-col gap-4 flex-1">
        <Textarea
          value={query}
          onChange={(e) => {
            setQuery(e.target.value);
          }}
          placeholder="Type your manipulation query here..."
          className="flex-1"
        />
        <Button
          className="w-fit ml-auto"
          disabled={fileLen === 0 || !query || isLoading}
          onClick={handleUpload}>
          Submit
        </Button>
      </CardContent>
    </Card>
  );
};

export default QueryCard;
