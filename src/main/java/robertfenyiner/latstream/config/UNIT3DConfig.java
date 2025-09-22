package robertfenyiner.latstream.config;package com.robertfenyiner.latstream.config;



import org.springframework.boot.context.properties.ConfigurationProperties;import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpHeaders;

@Componentimport org.springframework.web.client.RestClient;

@ConfigurationProperties(prefix = "unit3d.api")

public class UNIT3DConfig {/**

     * Configuration for UNIT3D Private Tracker API

    private String key; */

    private String baseUrl;@Configuration

    private boolean enabled;public class UNIT3DConfig {

    

    public String getKey() {    @Value("${unit3d.api.base-url}")

        return key;    private String baseUrl;

    }

        @Value("${unit3d.api.key}")

    public void setKey(String key) {    private String apiKey;

        this.key = key;

    }    @Value("${unit3d.api.enabled:false}")

        private boolean enabled;

    public String getBaseUrl() {

        return baseUrl;    /**

    }     * Creates a custom RestClient for UNIT3D API with authentication headers

         */

    public void setBaseUrl(String baseUrl) {    public RestClient createAuthenticatedRestClient(RestClient.Builder builder) {

        this.baseUrl = baseUrl;        if (!enabled) {

    }            throw new IllegalStateException("UNIT3D API is not enabled. Set unit3d.api.enabled=true");

            }

    public boolean isEnabled() {

        return enabled;        return builder

    }                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)

                    .defaultHeader(HttpHeaders.ACCEPT, "application/json")

    public void setEnabled(boolean enabled) {                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")

        this.enabled = enabled;                .defaultHeader("User-Agent", "LatStream/1.0")

    }                .build();

}    }

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
