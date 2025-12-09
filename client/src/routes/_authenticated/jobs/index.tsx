import { getStatus } from "@/components/dashboard/job-card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { DataTable } from "@/components/ui/data-table";
import { getJobs, getOutputFile, rerunJob } from "@/lib/api/jobs";
import useStore from "@/lib/store/useStore";
import type { IJob } from "@/lib/types";
import { formatFullDate } from "@/lib/utils";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { createFileRoute, Link } from "@tanstack/react-router";
import type { ColumnDef } from "@tanstack/react-table";
import { MoreHorizontalIcon } from "lucide-react";
import { useCallback, useEffect, useState } from "react";
import { toast } from "sonner";

export const Route = createFileRoute("/_authenticated/jobs/")({
  component: RouteComponent,
  head() {
    return {
      meta: [
        {
          title: "Jobs - Delta Processor",
        },
      ],
    };
  },
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

  const getData = useCallback(() => {
    setLoading(true);
    getJobs({
      offset: pagination.pageIndex,
      pageSize: pagination.pageSize,
      sortDirection: "desc",
    })
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

  useEffect(() => {
    getData();
  }, [getData]);

  const columns: ColumnDef<IJob>[] = [
    {
      accessorKey: "jobKey",
      header: "Key",
      cell({ row }) {
        return (
          <Link
            to="/jobs/$jobId"
            params={{ jobId: row.original.jobKey }}
            className="hover:underline">
            {row.getValue("jobKey")}
          </Link>
        );
      },
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
          <code
            className="bg-muted relative rounded px-[0.3rem] py-[0.2rem] font-mono text-sm font-semibold block w-fit max-w-[200px] truncate"
            title={row.original.file?.fileName}>
            {row.original.file?.fileName}
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
    {
      header: "Actions",
      cell({ row }) {
        return (
          <DropdownMenu>
            <DropdownMenuTrigger>
              <Button variant="outline" aria-label="Open menu" size="icon">
                <MoreHorizontalIcon />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuItem
                onClick={() => {
                  rerunJob(row.original.jobKey).then(() => {
                    toast.success("Job re-ran successfully");
                  });
                }}>
                Rerun
              </DropdownMenuItem>
              <DropdownMenuItem
                onClick={() => {
                  getOutputFile(row.original.jobKey);
                }}>
                Download
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        );
      },
    },
  ];

  return (
    <DataTable
      data={data}
      columns={columns}
      pagination={pagination}
      loading={loading}
      onPaginationChange={setPagination}
      totalRows={totalRows}
      height={"calc(100vh - 9rem"}
      refresh={getData}
    />
  );
}
