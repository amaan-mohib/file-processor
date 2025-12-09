package com.example.fileprocessor.repository;

import com.example.fileprocessor.entity.Notification;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.response.NotificationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("""
        SELECT new com.example.fileprocessor.payload.response.NotificationResponse(
            n.id, n.message, n.read, j.jobKey, n.createdAt
        )
        FROM Notification n JOIN n.job j WHERE n.user = :user AND n.read = false ORDER BY n.createdAt DESC
    """)
    List<NotificationResponse> findUnreadNotifications(User user, Pageable pageable);

    Optional<Notification> findByIdAndUser(Long id, User user);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.read = true WHERE n.user = :user AND n.read = false")
    void markAllAsRead(User user);
}
