import { DataTable } from "@/components/ui/data-table";
import { getFiles } from "@/lib/api/files";
import useStore from "@/lib/store/useStore";
import type { IFile } from "@/lib/types";
import { formatFullDate } from "@/lib/utils";
import { createFileRoute, Link } from "@tanstack/react-router";
import type { ColumnDef } from "@tanstack/react-table";
import { useEffect, useState } from "react";

export const Route = createFileRoute("/_authenticated/files/")({
  component: RouteComponent,
});

function RouteComponent() {
  const [data, setData] = useState<IFile[]>([]);
  const [pagination, setPagination] = useState({
    pageIndex: 0,
    pageSize: 15,
  });
  const [totalRows, setTotalRows] = useState(0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    useStore.setState({ breadcrumbs: [{ name: "Files", link: "/files" }] });
  }, []);

  useEffect(() => {
    setLoading(true);
    getFiles({
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

  const columns: ColumnDef<IFile>[] = [
    {
      accessorKey: "fileKey",
      header: "Key",
      cell({ row }) {
        return (
          <Link
            to="/files/$fileId"
            params={{ fileId: row.original.fileKey }}
            className="hover:underline">
            {row.getValue("fileKey")}
          </Link>
        );
      },
    },
    {
      accessorKey: "fileName",
      header: "File Name",
      cell({ row }) {
        return (
          <code className="bg-muted relative rounded px-[0.3rem] py-[0.2rem] font-mono text-sm font-semibold">
            {row.getValue("fileName")}
          </code>
        );
      },
    },
    {
      accessorKey: "fileType",
      header: "File Type",
    },

    {
      accessorKey: "createdAt",
      header: "Created at",
      cell({ row }) {
        const date = row.getValue("createdAt") as string;
        return date ? formatFullDate(date) : "-";
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
      height={"calc(100vh - 10rem"}
    />
  );
}
