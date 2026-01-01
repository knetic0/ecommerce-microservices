package com.mehmetsolak.fileservice.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public final class UploadRequest {

    private MultipartFile file;
    private String userId;
}
