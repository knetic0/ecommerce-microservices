package com.mehmetsolak.fileservice.storage;

import com.mehmetsolak.fileservice.dto.FileUploadResponse;
import com.mehmetsolak.fileservice.enums.FileType;
import com.mehmetsolak.results.Result;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    Result<FileUploadResponse> upload(MultipartFile file, FileType fileType);

    default String getFolderByFileType(FileType fileType) {
        return switch(fileType) {
            case PROFILE_PICTURE -> "profile_picture";
            default -> "";
        };
    }
}
