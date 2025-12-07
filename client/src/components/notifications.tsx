import React from "react";
import { Button } from "./ui/button";
import { Bell } from "lucide-react";

interface NotificationsProps {}

const Notifications: React.FC<NotificationsProps> = () => {
  return (
    <Button size={"icon"} variant={"outline"}>
      <Bell />
    </Button>
  );
};

export default Notifications;
