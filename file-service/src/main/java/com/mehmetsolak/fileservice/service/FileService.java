package com.mehmetsolak.fileservice.service;

import com.mehmetsolak.fileservice.dto.UploadRequest;
import com.mehmetsolak.fileservice.entity.FileMetadata;
import com.mehmetsolak.fileservice.enums.FileType;
import com.mehmetsolak.results.Result;

public interface FileService {
    Result<FileMetadata> upload(UploadRequest request, FileType fileType);
}
