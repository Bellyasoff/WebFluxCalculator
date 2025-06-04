package io.bellyasoff.webfluxcalculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bellyasoff.webfluxcalculator.entity.CalculatorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class WebFluxCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxCalculatorApplication.class, args);
    }

    @Bean
    public CalculatorConfig calculatorConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new ClassPathResource("config.json").getInputStream()) {
            return objectMapper.readValue(is, CalculatorConfig.class);
        }
    }

}
