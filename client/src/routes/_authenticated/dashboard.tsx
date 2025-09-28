import FileUpload from "@/components/file-upload";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/_authenticated/dashboard")({
  component: RouteComponent,
});

function RouteComponent() {
  return (
    <div className="flex flex-1 flex-col gap-4 xl:flex-row">
      <div className="flex w-full flex-col gap-4  ">
        <Card>
          <CardHeader>
            <CardTitle>Upload Files</CardTitle>
          </CardHeader>
          <CardContent>
            <FileUpload />
          </CardContent>
        </Card>
        <Card className="flex-1">
          <CardHeader>
            <CardTitle>Query</CardTitle>
          </CardHeader>
          <CardContent className="flex flex-col gap-4 flex-1">
            <Textarea
              placeholder="Type your manipulation query here..."
              className="flex-1"
            />
            <Button className="w-fit ml-auto">Submit</Button>
          </CardContent>
        </Card>
      </div>
      <div className="w-full h-full flex xl:max-w-sm xl:sticky xl:top-16 self-start">
        <Card className="flex-1">
          <CardHeader>
            <CardTitle>Recent Jobs</CardTitle>
          </CardHeader>
          <CardContent></CardContent>
        </Card>
      </div>
    </div>
  );
}
