package com.mehmetsolak.emailservice.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.mehmetsolak.emailservice.enums.EmailStatus;
import com.mehmetsolak.emailservice.enums.EmailType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode payload;

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
