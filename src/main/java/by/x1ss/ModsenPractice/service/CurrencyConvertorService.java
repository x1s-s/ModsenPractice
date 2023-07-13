package by.x1ss.ModsenPractice.service;

import java.math.BigDecimal;

public interface CurrencyConvertorService {
    BigDecimal convert(String baseCurrency, String exchangeCurrency, BigDecimal amount);
    BigDecimal getExchangeRate(String baseCurrency, String exchangeCurrency);
}
