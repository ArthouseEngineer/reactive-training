package com.study.reactivetraining.service;

import com.study.reactivetraining.dto.User;
import com.study.reactivetraining.excecption.FindException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class UsersService {


    private final WebClient jsonPlaceholderWebClient;

    public Mono<User> user(Long id) {
        return jsonPlaceholderWebClient
                .get()
                .uri("/users/{id}", id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorMap(e -> new FindException("Error when getting user", e));
    }

    public Flux<User> users() {
        return jsonPlaceholderWebClient
                .get()
                .uri("/users")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class)
                .onErrorMap(e -> new FindException("Error when getting users", e));
    }
}
