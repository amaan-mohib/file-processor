package com.example.fileprocessor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "jobs")
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private FileMetadata file;
    @Column(columnDefinition = "TEXT")
    private String manipulationQuery;  // "REMOVE key; SET key2=key1+key3;"
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JobStatus status;
    private String resultPath;
    private Long processedSize;
    @ColumnDefault("now()")
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
    private Instant completedAt;

    public enum JobStatus {
        PENDING,
        COMPLETED,
        IN_PROGRESS,
        FAILED
    }
}