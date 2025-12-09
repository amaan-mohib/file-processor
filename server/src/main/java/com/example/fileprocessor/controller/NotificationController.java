package com.example.fileprocessor.controller;

import com.example.fileprocessor.entity.Notification;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.response.GenericResponse;
import com.example.fileprocessor.payload.response.NotificationResponse;
import com.example.fileprocessor.repository.NotificationRepository;
import com.example.fileprocessor.service.NotificationService;
import com.example.fileprocessor.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/")
    public ResponseEntity<?> getAllNotifications() {
        User user = SecurityUtil.getCurrentUser();
        List<NotificationResponse> notifications = notificationService.getUnreadNotifications(user);
        return new ResponseEntity<>(new GenericResponse<>("ok", 200, notifications), HttpStatus.OK);
    }

    @PostMapping("/read/{id:.+}")
    public ResponseEntity<?> readNotification(@PathVariable Long id) {
        User user = SecurityUtil.getCurrentUser();
        notificationService.markAsRead(id, user);
        List<NotificationResponse> notifications = notificationService.getUnreadNotifications(user);
        return new ResponseEntity<>(new GenericResponse<>("Notification marked as read", 200, notifications), HttpStatus.OK);
    }

    @PostMapping("/mark-all-read")
    public ResponseEntity<?> markAllAsRead() {
        User user = SecurityUtil.getCurrentUser();
        notificationService.markAllAsRead(user);
        List<NotificationResponse> notifications = notificationService.getUnreadNotifications(user);
        return new ResponseEntity<>(new GenericResponse<>("All notifications marked as read", 200, notifications), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement(NoSuchElementException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(new GenericResponse<>("Notification not found", 404), HttpStatus.NOT_FOUND);
    }
}
