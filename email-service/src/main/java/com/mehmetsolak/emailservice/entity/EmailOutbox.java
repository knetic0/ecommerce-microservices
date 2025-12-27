package com.mehmetsolak.emailservice.entity;

import com.mehmetsolak.emailservice.enums.EmailStatus;
import com.mehmetsolak.emailservice.enums.EmailType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "email_outbox",
        indexes = {
                @Index(name = "idx_email_outbox_eventId", columnList = "eventId", unique = true)
        }
)
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public final class EmailOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID eventId;

    @Column(columnDefinition = "jsonb")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailType type;

    private Integer retryCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
