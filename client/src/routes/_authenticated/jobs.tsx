import { getStatus } from "@/components/dashboard/job-card";
import { Badge } from "@/components/ui/badge";
import { DataTable } from "@/components/ui/data-table";
import { getJobs } from "@/lib/api/jobs";
import useStore from "@/lib/store/useStore";
import type { IJob } from "@/lib/types";
import { formatFullDate } from "@/lib/utils";
import { createFileRoute } from "@tanstack/react-router";
import type { ColumnDef } from "@tanstack/react-table";
import { useEffect, useState } from "react";

export const Route = createFileRoute("/_authenticated/jobs")({
  component: RouteComponent,
});

function RouteComponent() {
  const [data, setData] = useState<IJob[]>([]);
  const [pagination, setPagination] = useState({
    pageIndex: 0,
    pageSize: 15,
  });
  const [totalRows, setTotalRows] = useState(0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    useStore.setState({ breadcrumbs: [{ name: "Jobs", link: "/jobs" }] });
  }, []);

  useEffect(() => {
    setLoading(true);
    getJobs({ offset: pagination.pageIndex, pageSize: pagination.pageSize })
      .then(({ content, totalElements }) => {
        setLoading(false);
        setTotalRows(totalElements);
        setData(content);
      })
      .catch((err) => {
        setLoading(false);
        console.error(err);
      });
  }, [pagination.pageIndex, pagination.pageSize]);

  const columns: ColumnDef<IJob>[] = [
    {
      accessorKey: "jobKey",
      header: "Key",
    },
    {
      accessorKey: "status",
      header: "Status",
      cell({ row }) {
        const status = getStatus(row.getValue("status"));
        return (
          <Badge
            className={`rounded-full ${status.color ? status.color : ""}`}
            variant={status.variant}>
            {status.text}
          </Badge>
        );
      },
    },
    {
      accessorKey: "fileName",
      header: "File",
      cell({ row }) {
        return (
          <code className="bg-muted relative rounded px-[0.3rem] py-[0.2rem] font-mono text-sm font-semibold">
            {row.getValue("fileName")}
          </code>
        );
      },
    },
    {
      accessorKey: "completedAt",
      header: "Completed at",
      cell({ row }) {
        const date = row.getValue("completedAt") as string;
        return date ? formatFullDate(date) : "-";
      },
    },
    {
      accessorKey: "createdAt",
      header: "Created at",
      cell({ row }) {
        const date = row.getValue("createdAt") as string;
        return date ? formatFullDate(date) : "-";
      },
    },
    {
      accessorKey: "startedAt",
      header: "Started at",
      cell({ row }) {
        const date = row.getValue("startedAt") as string;
        return date ? formatFullDate(date) : "-";
      },
    },
  ];

  return (
    <div className="flex flex-1 flex-col gap-4 max-h-[calc(100vh-5rem)]">
      <DataTable
        data={data}
        columns={columns}
        pagination={pagination}
        loading={loading}
        onPaginationChange={setPagination}
        totalRows={totalRows}
      />
    </div>
  );
}
