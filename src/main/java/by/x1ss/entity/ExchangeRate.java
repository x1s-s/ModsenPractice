package by.x1ss.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRate {
    private final String exchangeCurrency;
    private final String exchangeableCurrency;
    private final BigDecimal rate;
}
