package com.barbosa.desafio_tech.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebClientConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;
    
    @Value("${fortnite.api.base-url}")
    private String fortniteApiBaseUrl;

    @Value("${fortnite.api.key:}")
    private String fortniteApiKey;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public WebClient fortniteWebClient() {
        WebClient.Builder builder = WebClient.builder()
                .baseUrl(fortniteApiBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        // Adiciona API Key apenas se existir
        if (fortniteApiKey != null && !fortniteApiKey.isEmpty()) {
            builder.defaultHeader("x-api-key", fortniteApiKey);
        }

        return builder.build();
    }
}
