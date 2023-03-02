package com.study.reactivetraining.service;

import com.study.reactivetraining.dto.Post;
import com.study.reactivetraining.excecption.FindException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final WebClient jsonPlaceholderWebClient;

    public Flux<Post> getAllPosts() {
        return jsonPlaceholderWebClient
                .get()
                .uri("/posts")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Post.class);
    }

    public Flux<Post> postsByUserId(Long id) {
        return this.jsonPlaceholderWebClient
                .get()
                .uri(builder -> builder.path("/posts").queryParam("userId", id).build())
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Post.class)
                .onErrorMap(e -> new FindException("Error when getting posts", e));
    }

}
