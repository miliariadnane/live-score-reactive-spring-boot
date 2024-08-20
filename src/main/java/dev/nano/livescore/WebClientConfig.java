package dev.nano.livescore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${football-api.base-url}")
    private String baseUrl;

    @Value("${football-api.api-key}")
    private String apiKey;

    @Value("${football-api.host}")
    private String host;

    @Bean
    public WebClient footballApiClient() {
        // log.info("Configuring WebClient with base URL: {}", baseUrl);
        // log.info("Using host: {}", host);
        // log.info("API Key configured: {}", apiKey.substring(0, 5) + "...");

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-RapidAPI-Key", apiKey)
                .defaultHeader("X-RapidAPI-Host", host)
                .filter((request, next) -> {
                    // log.debug("Making request to: {}", request.url());
                    // log.debug("Headers: {}", request.headers());
                    return next.exchange(request);
                })
                .build();
    }
}
