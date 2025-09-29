import React from "react";
import { Card, CardContent, CardHeader, CardTitle } from "../ui/card";
import FileUpload from "./file-upload";

interface UploadCardProps {}

const UploadCard: React.FC<UploadCardProps> = () => {
  return (
    <Card>
      <CardHeader>
        <CardTitle>Upload Files</CardTitle>
      </CardHeader>
      <CardContent>
        <FileUpload />
      </CardContent>
    </Card>
  );
};

export default UploadCard;
