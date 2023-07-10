package by.x1ss.ModsenPractice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ExchangeRate {
    private final String baseCurrency;
    private final String exchangeCurrency;
    private final BigDecimal rate;
}
