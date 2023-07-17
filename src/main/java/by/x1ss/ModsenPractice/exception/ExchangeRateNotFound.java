package by.x1ss.ModsenPractice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExchangeRateNotFound extends ServiceBaseException{
    private String baseCurrency;
    private String exchangeCurrency;

    @Override
    public String getMessage() {
        return "Not found exchange rate from " + baseCurrency + " to " + exchangeCurrency;
    }
}
