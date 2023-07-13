package by.x1ss.ModsenPractice.service.impl;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.exception.ExchangeRateNotFound;
import by.x1ss.ModsenPractice.service.CurrencyConvertorService;
import by.x1ss.ModsenPractice.service.ExchangeRateGetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyConvertorServiceImpl implements CurrencyConvertorService {
    private final ExchangeRateGetService exchangeRateGetService;

    public BigDecimal convert(String baseCurrency, String exchangeCurrency, BigDecimal amount) {
        return amount.multiply(getExchangeRate(baseCurrency, exchangeCurrency));
    }

    public BigDecimal getExchangeRate(String baseCurrency, String exchangeCurrency) {
        List<ExchangeRateDto> exchangeRates = exchangeRateGetService.getExchangeRates();
        for (ExchangeRateDto exchangeRate : exchangeRates) {
            if (exchangeRate.getBaseCurrency().equals(baseCurrency) && exchangeRate.getExchangeCurrency().equals(exchangeCurrency)) {
                return exchangeRate.getExchangeRate();
            }
        }
        throw new ExchangeRateNotFound(baseCurrency, exchangeCurrency);
    }
}
