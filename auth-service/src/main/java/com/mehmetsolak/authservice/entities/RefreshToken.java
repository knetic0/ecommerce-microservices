package com.mehmetsolak.authservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(name = "idx_refresh_token_user", columnList = "userId"),
                @Index(name = "idx_refresh_token", columnList = "token", unique = true)
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    private String token;

    private boolean used;

    private boolean revoked;

    private LocalDateTime expiresAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isValid() {
        return !revoked
                && !used
                && expiresAt.isAfter(LocalDateTime.now());
    }
}
