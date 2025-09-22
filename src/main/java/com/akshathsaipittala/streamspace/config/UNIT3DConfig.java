package com.akshathsaipittala.streamspace.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

/**
 * Configuration for UNIT3D Private Tracker API
 */
@Configuration
public class UNIT3DConfig {

    @Value("${unit3d.api.base-url}")
    private String baseUrl;

    @Value("${unit3d.api.key}")
    private String apiKey;

    @Value("${unit3d.api.enabled:false}")
    private boolean enabled;

    /**
     * Creates a custom RestClient for UNIT3D API with authentication headers
     */
    public RestClient createAuthenticatedRestClient(RestClient.Builder builder) {
        if (!enabled) {
            throw new IllegalStateException("UNIT3D API is not enabled. Set unit3d.api.enabled=true");
        }

        return builder
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader("User-Agent", "StreamSpace/1.0")
                .build();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean isEnabled() {
        return enabled;
    }
}