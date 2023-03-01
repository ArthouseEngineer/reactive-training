package com.study.reactivetraining;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static java.time.Duration.ofMillis;
import static java.util.stream.Collectors.toList;


class MonoFluxBaseExample {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("Подписка на Flux")
    void fluxSubscribe() {
        Flux
                .just("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
                .subscribe(val -> log.info("Value is : {}", val));
    }

    @Test
    @DisplayName("Подписка на Flux c обработкой EX")
    void fluxSubscribeWithExHandling() {
        Flux
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribe(val -> {
                    if (val > 4) {
                        throw new IllegalArgumentException(val + " > than 4");
                    }
                    log.info("Value is : {}", val);
                }, err -> log.error("Error : {}", err.getMessage(), err));
    }

    @Test
    @DisplayName("Подписка на Flux таймаутом и дальнейшей отменой")
    void fluxSubscribeWithTimeout() throws InterruptedException {
        Disposable dispose = Flux
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .delayElements(Duration.ofSeconds(2))
                .subscribe(val -> log.info("Value is : {}", val));

        Thread.sleep(6000);
        dispose.dispose();
    }

    @Test
    @DisplayName("Подписка на Flux и преобразование")
    void fluxSubscribeAndMap() {
        Flux
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .map(i -> i + 1)
                .subscribe(val -> log.info("Value is : {}", val));
    }

    @Test
    @DisplayName("Подписка на Flux FlatMap")
    void fluxSubscribeAndFlatMap() {
        Flux
                .just("1,2,3", "4,5", "6,7,8", "9,10")
                .flatMap(i -> Flux.fromIterable(Arrays.asList(i.split(","))))
                .collect(toList())
                .subscribe(val -> log.info("Value is : {}", val));
    }

    @Test
    @DisplayName("Распаралеливание Flux")
    void parallelFlux() {
        Flux.range(1, 10)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .subscribe(value -> log.info("{} - {}", Thread.currentThread().getName(), value));
    }

    @Test
    @DisplayName("Default if Empty Flux")
    void defaultIfEmptyFlux() {
        Flux.fromIterable(List.of())
                .defaultIfEmpty("Default")
                .subscribe(value -> log.info("Value - {}", value));
    }

    @Test
    @DisplayName("sequenceEqualsMono")
    void sequenceEqualsMono() {
        Mono.sequenceEqual(
                        Flux.just("1", "2", "3"),
                        Flux.just("1", "2", "4")
                )
                .subscribe(value -> log.info("Value - {}", value));
    }

    @Test
    @DisplayName("Merge Flux")
    void mergeFlux() throws InterruptedException {
        Flux.merge(
                        Flux.just(1, 2, 3).delayElements(ofMillis(10)),
                        Flux.just(10, 20, 30).delayElements(ofMillis(5))
                )
                .subscribe(value -> log.info("Value - {}", value));

        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Concat Flux")
    void concatFlux() throws InterruptedException {
        Flux.concat(
                        Flux.just(1, 2, 3).delayElements(ofMillis(10)),
                        Flux.just(10, 20, 30).delayElements(ofMillis(5))
                )
                .subscribe(value -> log.info("Value - {}", value));

        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Zip Flux")
    void zipFlux() throws InterruptedException {
        Flux.zip(
                        Flux.just(1, 2, 3, 4).delayElements(ofMillis(10)),
                        Flux.just(10, 20, 30).delayElements(ofMillis(5))
                )
                .subscribe(value -> log.info("Value - {}", value));

        Thread.sleep(1000);
    }

    @Test
    @DisplayName("Distinct Flux")
    void distinctFlux() {
        Flux
                .just(1, 2, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10)
                .distinct()
                .subscribe(val -> log.info("Value is : {}", val));
    }

    @Test
    @DisplayName("Filter Flux")
    void filterFlux() {
        Flux
                .just(1, 2, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10)
                .filter(i -> i % 2 == 0)
                .subscribe(val -> log.info("Value is : {}", val));
    }

    @Test
    @DisplayName("Filter Flux")
    void takeWithFilterAndDelayFlux() throws InterruptedException {
        Flux
                .just(1, 2, 3, 4, 4, 5, 6, 6, 7, 8, 9, 10)
                .delayElements(Duration.ofMillis(300))
                .filter(i -> i % 2 == 0)
                .take(2)
                .subscribe(val -> log.info("Value is : {}", val));

        Thread.sleep(3000);
    }

    @Test
    @DisplayName("Reduce Flux")
    void reduceFlux() {
        Flux
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .reduce(Integer::sum)
                .subscribe(val -> log.info("Sum of elements is : {}", val));
    }
}
