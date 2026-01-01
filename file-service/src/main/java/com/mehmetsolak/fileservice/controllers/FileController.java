package com.mehmetsolak.fileservice.controllers;

import com.mehmetsolak.constants.CustomHeaders;
import com.mehmetsolak.fileservice.dto.UploadFileRequest;
import com.mehmetsolak.fileservice.dto.UploadRequest;
import com.mehmetsolak.fileservice.dto.UploadResponse;
import com.mehmetsolak.fileservice.entity.FileMetadata;
import com.mehmetsolak.fileservice.enums.FileType;
import com.mehmetsolak.fileservice.producers.UserProfilePictureEventProducer;
import com.mehmetsolak.fileservice.service.FileService;
import com.mehmetsolak.results.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final UserProfilePictureEventProducer userProfilePictureEventProducer;

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<Result<UploadResponse>> uploadProfilePicture(
            @Valid @ModelAttribute UploadFileRequest req,
            @RequestHeader(name = CustomHeaders.USER_ID) String userId
    ) {
        UploadRequest request = UploadRequest
                .builder()
                .file(req.getFile())
                .userId(userId)
                .build();

        Result<FileMetadata> result = fileService.upload(request, FileType.PROFILE_PICTURE);
        if(!result.isSuccess()) {
            return ResponseEntity.badRequest().body(Result.failure());
        }

        FileMetadata fileMetadata = result.getData();
        String url = fileMetadata.getUrl();

        UploadResponse resp = UploadResponse
                .builder()
                .url(url)
                .build();

        userProfilePictureEventProducer.send(userId, url);

        return ResponseEntity.ok(Result.success(resp));
    }
}
