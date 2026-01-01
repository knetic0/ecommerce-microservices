package com.mehmetsolak.fileservice.entity;

import com.mehmetsolak.fileservice.enums.StorageType;
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
@Table(name = "file_metadatas")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public final class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    private String fileName;

    private Long size;

    private String url;

    private String contentType;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
