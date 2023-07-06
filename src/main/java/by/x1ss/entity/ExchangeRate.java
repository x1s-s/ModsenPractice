package by.x1ss.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRate {
    private final String baseCurrency;
    private final String exchangeCurrency;
    private final BigDecimal rate;
}
