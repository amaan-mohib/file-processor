package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.entity.Notification;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.response.NotificationResponse;
import com.example.fileprocessor.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<NotificationResponse> getUnreadNotifications(User user) {
        return notificationRepository.findUnreadNotifications(user, PageRequest.of(0, 10));
    }

    public Notification create(User user, Job job, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setJob(job);
        notification.setMessage(message);
        notification.setRead(false);
        return notificationRepository.save(notification);
    }

    public void markAsRead(Long id, User user) throws NoSuchElementException {
        Notification notification = notificationRepository.findByIdAndUser(id, user).orElseThrow();
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void markAsUnread(Notification notification) {
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(User user) {
        notificationRepository.markAllAsRead(user);
    }
}
