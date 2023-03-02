package com.study.reactivetraining.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class JsonPlaceholderWebClient {


    @Bean
    public WebClient jsonPlaceholderClient() {
        return WebClient
                .builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }


}
