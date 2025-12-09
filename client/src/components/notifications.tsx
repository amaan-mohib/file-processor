import React, { useEffect, useState } from "react";
import { Button } from "./ui/button";
import { Bell } from "lucide-react";
import useStore from "@/lib/store/useStore";
import { Badge } from "./ui/badge";
import { Popover, PopoverContent, PopoverTrigger } from "./ui/popover";
import { Separator } from "./ui/separator";
import { formatFullDate } from "@/lib/utils";
import { Link } from "@tanstack/react-router";
import {
  getUnreadNotifications,
  markAllNotificationsAsRead,
  markNotificationAsRead,
} from "@/lib/api/notification";

interface NotificationsProps {}

const Notifications: React.FC<NotificationsProps> = () => {
  const notifications = useStore((state) => state.notifications);
  const [loading, setLoading] = useState(false);
  const [open, setOpen] = useState(false);
  const [timer, setTimer] = useState(0);

  const updateNotifications = async () => {
    try {
      const notifications = await getUnreadNotifications();
      useStore.setState({ notifications });
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    updateNotifications();

    const timeout = setTimeout(() => {
      setTimer((prev) => prev + 1);
    }, 10_000);

    return () => {
      clearTimeout(timeout);
    };
  }, [timer]);

  const handleMarkRead = async (id: number) => {
    try {
      setLoading(true);
      const updated = await markNotificationAsRead(id);
      useStore.setState({ notifications: updated });
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleMarkAllRead = async () => {
    try {
      setLoading(true);
      const updated = await markAllNotificationsAsRead();
      useStore.setState({ notifications: updated });
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button className="relative" size="icon" variant={"outline"}>
          <Bell className="h-5 w-5" />
          {notifications.length > 0 && (
            <Badge className="-right-1 -top-1 absolute h-5 min-w-5 items-center justify-center rounded-full p-0 text-xs">
              {notifications.length}
            </Badge>
          )}
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-120" align="end">
        <div className="space-y-2">
          <div className="flex items-center justify-between">
            <h4 className="font-semibold">Notifications</h4>
            <Button
              onClick={handleMarkAllRead}
              size="sm"
              variant="ghost"
              disabled={notifications.length === 0 || loading}>
              Mark all as read
            </Button>
          </div>
          <Separator />
          <div className="gap-2 flex flex-col">
            {notifications.length === 0 ? (
              <p className="text-sm text-muted-foreground">
                No new notifications
              </p>
            ) : (
              notifications.map((item) => (
                <Link
                  to={`/jobs/$jobId`}
                  params={{ jobId: item.jobKey }}
                  key={item.id}
                  onClick={() => {
                    handleMarkRead(item.id);
                    setOpen(false);
                  }}>
                  <div className="text-sm p-2 rounded-md border hover:bg-accent hover:text-accent-foreground dark:hover:bg-accent/50 dark:border-input">
                    <p className="font-medium">{item.message}</p>
                    <p className="text-muted-foreground text-xs">
                      {formatFullDate(item.createdAt)}
                    </p>
                    <Button
                      disabled={loading}
                      onClick={(e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        handleMarkRead(item.id);
                      }}
                      variant={"ghost"}
                      size={"sm"}
                      className="w-full mt-1">
                      Mark read
                    </Button>
                  </div>
                </Link>
              ))
            )}
          </div>
        </div>
      </PopoverContent>
    </Popover>
  );
};

export default Notifications;
