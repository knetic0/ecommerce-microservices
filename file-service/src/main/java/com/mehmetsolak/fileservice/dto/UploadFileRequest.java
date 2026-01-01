package com.mehmetsolak.fileservice.dto;

import com.mehmetsolak.fileservice.validators.MaxFileSize;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public final class UploadFileRequest {

    @NonNull
    @MaxFileSize(
            value = 1_048_576,
            message = "The profile picture must be small than 1 MB!"
    )
    private MultipartFile file;
}
