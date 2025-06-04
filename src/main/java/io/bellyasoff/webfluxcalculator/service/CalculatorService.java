package io.bellyasoff.webfluxcalculator.service;

import io.bellyasoff.webfluxcalculator.entity.CalculatorConfig;
import io.bellyasoff.webfluxcalculator.entity.Result;
import io.bellyasoff.webfluxcalculator.executor.ScriptEngineFactory;
import io.bellyasoff.webfluxcalculator.executor.ScriptExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;

/**
 * @author IlyaB
 */

@Service
@RequiredArgsConstructor
public class CalculatorService {

    private final CalculatorConfig config;
    private final ScriptEngineFactory scriptEngineFactory;

    public Flux<String> calculate(int count, boolean ordered) {
        if (ordered) {
            return calculateOrdered(count);
        } else {
            return calculateUnordered(count);
        }
    }

    private Flux<String> calculateOrdered(int count) {
        return Flux.range(1, count)
                .delayElements(Duration.ofMillis(config.interval))
                .flatMap(i -> {
                    Mono<Result> r1 = executeFunction(i, 1, config.function1);
                    Mono<Result> r2 = executeFunction(i, 2, config.function2);

                    return Mono.zip(r1, r2)
                            .map(tuple -> {
                                Result res1 = tuple.getT1();
                                Result res2 = tuple.getT2();

                                int buffered1 = 0;
                                int buffered2 = 0;

                                if (res1.isError()) {
                                    return String.format("%d,%d,error: %s",
                                            res1.getIteration(), res1.getFunctionNumber(), res1.getErrorMessage());
                                }

                                if (res2.isError()) {
                                    return String.format("%d,%d,error: %s",
                                            res2.getIteration(), res2.getFunctionNumber(), res2.getErrorMessage());
                                }

                                return String.format("<%d>,<%.5f>,<%d>,<%d>,<%.5f>,<%d>,<%d>",
                                        i, res1.value, res1.duration, buffered1,
                                        res2.value, res2.duration, buffered2);
                            });
                });
    }

    private Flux<String> calculateUnordered(int count) {
        Flux<Result> flux1 = Flux.range(1, count)
                .delayElements(Duration.ofMillis(config.interval))
                .flatMap(i -> executeFunction(i, 1, config.function1));

        Flux<Result> flux2 = Flux.range(1, count)
                .delayElements(Duration.ofMillis(config.interval))
                .flatMap(i -> executeFunction(i, 2, config.function2));

        return Flux.merge(flux1, flux2)
                .map(res -> {
                    if (res.isError()) {
                        return String.format("%d,%d,error: %s", res.getIteration(), res.getFunctionNumber(), res.getErrorMessage());
                    }
                    return String.format("<%d>,<%d>,<%.5f>,<%d>", res.getIteration(), res.getFunctionNumber(), res.getValue(), res.getDuration());
                });
    }

    private Mono<Result> executeFunction(int iteration, int funcNum, String function) {
        return Mono.defer(() -> Mono.fromCallable(() -> {
            Instant start = Instant.now();

            ScriptExecutor executor = scriptEngineFactory.getExecutor(function);
            double value = executor.execute(function, iteration);
            long timeMs = Duration.between(start, Instant.now()).toMillis();
            return Result.success(iteration, funcNum, value, timeMs);
        }))
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(e -> {
                    String errorMsg = e.getClass().getSimpleName() + ": " + e.getMessage();
                    return Mono.just(Result.error(iteration, funcNum, errorMsg));
                });
    }

}
