package com.example.fileprocessor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "notifications", indexes = @Index(columnList = "user_id, read, createdAt", name = "idx_notifications_user_read_createdAt"))
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean read;

    @Column(columnDefinition = "TEXT")
    private String message;

    @ColumnDefault("now()")
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @UpdateTimestamp
    private Instant updatedAt = Instant.now();
}
