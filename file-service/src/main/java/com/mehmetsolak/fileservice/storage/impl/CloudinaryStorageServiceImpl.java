package com.mehmetsolak.fileservice.storage.impl;

import com.mehmetsolak.fileservice.dto.CloudinaryUploadResponse;
import com.mehmetsolak.fileservice.dto.FileUploadResponse;
import com.mehmetsolak.fileservice.enums.FileType;
import com.mehmetsolak.fileservice.security.CloudinarySignatureService;
import com.mehmetsolak.fileservice.storage.StorageService;
import com.mehmetsolak.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service("cloudinary")
@RequiredArgsConstructor
public final class CloudinaryStorageServiceImpl implements StorageService {

    private final RestClient cloudinaryRestClient;
    private final CloudinarySignatureService cloudinarySignatureService;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Override
    public Result<FileUploadResponse> upload(MultipartFile file, FileType fileType) {
        try {
            String folderName = getFolderByFileType(fileType);
            String timestamp = cloudinarySignatureService.currentTimestamp();
            Map<String, String> params = Map.of("timestamp", timestamp, "folder", folderName);

            String signature = cloudinarySignatureService.generateSignature(params);

            CloudinaryUploadResponse response = cloudinaryRestClient
                    .post()
                    .uri("/upload")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(createMultipartBody(file, timestamp, signature, folderName))
                    .retrieve()
                    .body(CloudinaryUploadResponse.class);

            if(Objects.isNull(response)) {
                return Result.failure("Something went wrong");
            }

            return Result.success(
                    FileUploadResponse
                            .builder()
                            .secureUrl(response.getSecureUrl())
                            .build()
            );
        } catch(Exception e) {
            return Result.failure("Something went wrong while uploading file");
        }
    }

    private MultiValueMap<String, Object> createMultipartBody(
            MultipartFile file,
            String timestamp,
            String signature,
            String folderName
    ) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        body.add("file", fileResource);
        body.add("api_key", apiKey);
        body.add("timestamp", timestamp);
        body.add("signature", signature);
        body.add("folder", folderName);

        return body;
    }
}
