package by.x1ss.ModsenPractice.controllers;

import by.x1ss.ModsenPractice.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @GetMapping("/calculate/{expression}")
    @ResponseStatus(HttpStatus.OK)
    public String calculate(@PathVariable String expression) {
        log.info("Calculate expression: {}", expression);
        return calculatorService.calculate(expression);
    }

}
