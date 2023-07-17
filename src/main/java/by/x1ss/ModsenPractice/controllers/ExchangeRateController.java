package by.x1ss.ModsenPractice.controllers;

import by.x1ss.ModsenPractice.service.CurrencyConvertorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/exchangeRates")
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateController {
    private final CurrencyConvertorService currencyConvertorService;

    @GetMapping("/{baseCurrency}/{exchangeCurrency}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getCurrency(@PathVariable String baseCurrency, @PathVariable String exchangeCurrency) {
        log.info("Get exchange rate for {} to {}", baseCurrency, exchangeCurrency);
        return currencyConvertorService.getExchangeRate(baseCurrency, exchangeCurrency);
    }
}
