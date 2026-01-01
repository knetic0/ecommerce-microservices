package com.mehmetsolak.fileservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpConfig {

    @Bean
    public RestClient cloudinaryRestClient(
            RestClient.Builder clientBuilder,
            @Value("${cloudinary.base-url}") String cloudinaryBaseUrl,
            @Value("${cloudinary.product-environment}") String cloudinaryProductEnvironment
    ) {
        return clientBuilder
                .baseUrl(cloudinaryBaseUrl + "/" + cloudinaryProductEnvironment)
                .build();
    }
}
