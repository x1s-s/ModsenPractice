package by.x1ss.ModsenPractice.controllers;

import by.x1ss.ModsenPractice.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @GetMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public String calculate(@RequestBody String expression) {
        return calculatorService.calculate(expression);
    }

}
