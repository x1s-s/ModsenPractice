package by.x1ss.ModsenPractice.service;

import java.math.BigDecimal;

public interface CurrencyConvertorService {
    BigDecimal convert(String baseCurrency, String exchangeCurrency, BigDecimal amount);
    String convertByCommand(String command, String amount);
    BigDecimal getExchangeRate(String baseCurrency, String exchangeCurrency);
}
