package com.study.reactivetraining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ReactiveTrainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveTrainingApplication.class, args);
    }

}
