package com.example.pagos.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    @ConditionalOnProperty(name = "app.discovery.enabled", havingValue = "true", matchIfMissing = true)
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @ConditionalOnProperty(name = "app.discovery.enabled", havingValue = "false")
    public WebClient.Builder directWebClientBuilder() {
        return WebClient.builder();
    }
}