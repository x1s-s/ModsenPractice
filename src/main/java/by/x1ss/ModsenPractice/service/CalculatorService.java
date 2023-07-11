package by.x1ss.ModsenPractice.service;

import by.x1ss.ModsenPractice.calculator.Calculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculatorService {
    private final Calculator calculator;

    public String calculate(String expression) {
        try{
            return calculator.calculate(expression);
        } catch (Exception e){
            log.warn(e.getMessage());
            return null;
        }
    }
}
