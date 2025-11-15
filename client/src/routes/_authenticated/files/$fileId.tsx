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
import { getFile, getInputFile } from "@/lib/api/files";
import useStore from "@/lib/store/useStore";
import type { IFile, IJob } from "@/lib/types";
import { createFileRoute } from "@tanstack/react-router";
import { useEffect, useState } from "react";

export const Route = createFileRoute("/_authenticated/files/$fileId")({
  component: RouteComponent,
});

function RouteComponent() {
  const { fileId } = Route.useParams();
  const [data, setData] = useState<{ file: IFile; jobs: IJob[] } | null>(null);

  useEffect(() => {
    getFile(fileId).then((file) => {
      setData(file);
      useStore.setState({
        breadcrumbs: [
          { name: "Files", link: "/files" },
          { name: file.file.fileKey, link: `/files/${file.file.fileKey}` },
        ],
      });
    });
  }, [fileId]);

  if (!data) {
    return <div>Loading...</div>;
  }

  const { jobs, file } = data;

  return (
    <div className="flex flex-col gap-4">
      <Card>
        <CardHeader>
          <CardDescription>File Name</CardDescription>
          <CardTitle>{file.fileName}</CardTitle>
          <CardAction>
            <Button
              onClick={() => {
                getInputFile(file.fileKey);
              }}
              size={"sm"}>
              Download
            </Button>
          </CardAction>
        </CardHeader>
        <CardContent>
          <CardDescription>File ID: {file.fileKey}</CardDescription>
          <CardDescription>Type: {file.fileType} </CardDescription>
        </CardContent>
      </Card>
      <CardTitle>Jobs</CardTitle>
      {jobs.map((job) => (
        <JobCard
          key={job.jobKey}
          job={{ ...job, fileName: file.fileName }}
          hideActions
        />
      ))}
    </div>
  );
}
