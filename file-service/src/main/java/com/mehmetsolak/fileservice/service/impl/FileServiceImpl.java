package com.mehmetsolak.fileservice.service.impl;

import com.mehmetsolak.fileservice.dto.UploadRequest;
import com.mehmetsolak.fileservice.dto.FileUploadResponse;
import com.mehmetsolak.fileservice.entity.FileMetadata;
import com.mehmetsolak.fileservice.enums.FileType;
import com.mehmetsolak.fileservice.enums.StorageType;
import com.mehmetsolak.fileservice.repository.FileMetadataRepository;
import com.mehmetsolak.fileservice.service.FileService;
import com.mehmetsolak.fileservice.storage.StorageService;
import com.mehmetsolak.results.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public final class FileServiceImpl implements FileService {

    private final StorageService storageService;
    private final FileMetadataRepository fileMetadataRepository;

    public FileServiceImpl(
            @Qualifier("cloudinary") StorageService storageService,
            FileMetadataRepository fileMetadataRepository
    ) {
        this.storageService = storageService;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @Override
    public Result<FileMetadata> upload(UploadRequest request, FileType fileType) {
        MultipartFile file = request.getFile();

        Result<FileUploadResponse> response = storageService.upload(file, fileType);
        if(!response.isSuccess()) {
            return Result.failure(response.getMessage());
        }

        FileUploadResponse uploadResponse = response.getData();

        FileMetadata entity = FileMetadata
                .builder()
                .userId(UUID.fromString(request.getUserId()))
                .url(uploadResponse.getSecureUrl())
                .storageType(StorageType.CLOUDINARY)
                .contentType(file.getContentType())
                .size(file.getSize())
                .fileName(file.getName())
                .build();

        FileMetadata savedEntity = fileMetadataRepository.save(entity);

        return Result.success(savedEntity);
    }
}
