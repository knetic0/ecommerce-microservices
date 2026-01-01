package com.mehmetsolak.fileservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class UploadResponse {

    private String url;
}
