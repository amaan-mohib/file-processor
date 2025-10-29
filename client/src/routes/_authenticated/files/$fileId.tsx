import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_authenticated/files/$fileId')({
  component: RouteComponent,
})

function RouteComponent() {
  return <div>Hello "/_authenticated/files/$fileId"!</div>
}
