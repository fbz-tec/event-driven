package com.eazybytes.gatewayserver.config;

import com.eazybytes.gatewayserver.service.client.CustomerSummaryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {
    @Value("${app.base-url}")
    private String baseUrl;

    @Bean
    CustomerSummaryClient customerSummaryClient() {
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        //Wrapper for the WebClient instance, will be used with the HttpServiceProxyFactory class
        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        //Generate the client proxy (implementation for the CustomerSummaryClient interface)
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(CustomerSummaryClient.class);
    }

}
