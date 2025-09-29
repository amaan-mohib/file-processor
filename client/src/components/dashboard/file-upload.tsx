import React, { useState } from "react";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "../ui/tabs";
import {
  Dropzone,
  DropzoneContent,
  DropzoneEmptyState,
} from "../ui/shadcn-io/dropzone";
import useStore from "@/lib/store/useStore";

interface IUploaderProps {
  type: string;
  setAllFiles: (arg: Record<string, File[]>) => void;
  defaultFiles: File[];
}

const Uploader: React.FC<IUploaderProps> = ({
  type,
  setAllFiles,
  defaultFiles,
}) => {
  const [files, setFiles] = useState<File[] | undefined>(
    defaultFiles.length > 0 ? defaultFiles : undefined
  );

  const handleDrop = (files: File[] | undefined) => {
    setAllFiles({ [type]: files || [] });
    setFiles(files);
  };

  return (
    <Dropzone
      className="mt-2"
      accept={{ [`text/${type.toLowerCase()}`]: [`.${type.toLowerCase()}`] }}
      multiple
      onError={console.error}
      onDrop={handleDrop}
      src={files}>
      <DropzoneEmptyState />
      <DropzoneContent
        clear={() => {
          handleDrop(undefined);
        }}
      />
    </Dropzone>
  );
};

const tabs = ["CSV", "JSON", "XML"];

interface FileUploadProps {}

const FileUpload: React.FC<FileUploadProps> = () => {
  const { files, setFiles } = useStore((state) => state.dashboard.upload);
  const isLoading = useStore((state) => state.dashboard.isLoading);

  return (
    <div className="flex flex-col gap-4">
      <Tabs defaultValue="CSV">
        <TabsList>
          {tabs.map((tab) => (
            <TabsTrigger key={tab} value={tab}>
              {`${tab}${files[tab].length !== 0 ? ` (${files[tab].length})` : ""}`}
            </TabsTrigger>
          ))}
        </TabsList>
        {tabs.map((tab) => (
          <TabsContent key={tab} value={tab}>
            <Uploader
              key={`${isLoading}`}
              defaultFiles={files[tab]}
              type={tab}
              setAllFiles={setFiles}
            />
            <p className="text-xs text-gray-500 dark:text-gray-400 mt-4">
              Upload one or more {tab} files.
            </p>
          </TabsContent>
        ))}
      </Tabs>
    </div>
  );
};

export default FileUpload;
