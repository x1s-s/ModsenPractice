package by.x1ss.ModsenPractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor
@Builder
public class ExchangeRateDto {
    String baseCurrency;
    String exchangeCurrency;
    BigDecimal exchangeRate;
}
