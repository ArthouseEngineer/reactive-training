package com.study.reactivetraining.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequestMapping("/api/v1/simple")
public class SimpleMockController {


    @GetMapping(path = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream() {
        return Flux
                .generate(() -> 0, (state, emitter) -> {
                    emitter.next(state);
                    return state + 1;
                })
                .delayElements(Duration.ofSeconds(1))
                .map(i -> "" + i);
    }


}
