package by.x1ss.ModsenPractice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CurrencySymbolNotFound extends RuntimeException{
    private String expression;

    @Override
    public String getMessage() {
        return "Can't find currency symbol of expression : " + expression;
    }
}
