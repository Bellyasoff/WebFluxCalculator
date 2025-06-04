package io.bellyasoff.webfluxcalculator.controller;

import io.bellyasoff.webfluxcalculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.*;

/**
 * @author IlyaB
 */

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService service;

    @GetMapping(value = "/calculate", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> calculate(@RequestParam int count, @RequestParam boolean ordered) {
        return service.calculate(count, ordered);
    }
}
