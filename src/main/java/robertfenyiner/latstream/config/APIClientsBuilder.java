package com.robertfenyiner.latstream.config;

import com.robertfenyiner.latstream.www.APIBayClient;
import com.robertfenyiner.latstream.www.MicrosoftStoreAPI;
import com.robertfenyiner.latstream.www.UNIT3DAPIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class APIClientsBuilder {

    @Autowired(required = false)
    private UNIT3DConfig unit3dConfig;

    @Bean
    RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(name = "unit3d.api.enabled", havingValue = "true")
    RestClient unit3dRestClient(RestClient.Builder builder) {
        return unit3dConfig.createAuthenticatedRestClient(builder);
    }

    @Bean
    @Qualifier("default")
    HttpServiceProxyFactory httpServiceProxyFactory(RestClient restClient) {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();
    }

    @Bean
    @Qualifier("unit3d")
    @ConditionalOnProperty(name = "unit3d.api.enabled", havingValue = "true")
    HttpServiceProxyFactory unit3dHttpServiceProxyFactory(RestClient unit3dRestClient) {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(unit3dRestClient))
                .build();
    }

    @Bean
    APIBayClient apiBayClient(@Qualifier("default") HttpServiceProxyFactory factory) {
        return factory.createClient(APIBayClient.class);
    }

    @Bean
    MicrosoftStoreAPI microsoftStoreAPI(@Qualifier("default") HttpServiceProxyFactory factory) {
        return factory.createClient(MicrosoftStoreAPI.class);
    }

    @Bean
    @ConditionalOnProperty(name = "unit3d.api.enabled", havingValue = "true")
    UNIT3DAPIClient unit3dAPIClient(@Qualifier("unit3d") HttpServiceProxyFactory unit3dHttpServiceProxyFactory) {
        return unit3dHttpServiceProxyFactory.createClient(UNIT3DAPIClient.class);
    }
}
