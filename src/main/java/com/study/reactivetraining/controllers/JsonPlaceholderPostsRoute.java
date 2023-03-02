package com.study.reactivetraining.controllers;

import com.study.reactivetraining.dto.Post;
import com.study.reactivetraining.service.PostsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class JsonPlaceholderPostsRoute {

    private static final String BASE_URL = "/api/v1/posts";

    @Bean
    public RouterFunction<ServerResponse> postsRouter(PostsService postsService) {
        return RouterFunctions.route()
                .GET(BASE_URL,
                        accept(APPLICATION_JSON),
                        serverRequest -> ok()
                                .contentType(APPLICATION_JSON)
                                .body(postsService.getAllPosts(), Post.class)
                )
                .GET(BASE_URL + "/{userId}",
                        accept(APPLICATION_JSON),
                        serverRequest -> {
                            var userId = Long.parseLong(serverRequest.pathVariable("userId"));
                            return ok()
                                    .contentType(APPLICATION_JSON)
                                    .body(postsService.postsByUserId(userId), Post.class);
                        }
                )
                .build();
    }

}
