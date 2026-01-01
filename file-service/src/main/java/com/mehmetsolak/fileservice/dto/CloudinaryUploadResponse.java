package com.mehmetsolak.fileservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class CloudinaryUploadResponse {
    @JsonProperty("public_id")
    private String publicId;

    @JsonProperty("secure_url")
    private String secureUrl;

    private String url;
    private Long bytes;
    private String format;
}
