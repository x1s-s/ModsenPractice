package by.x1ss.ModsenPractice.controllers;

import by.x1ss.ModsenPractice.service.CurrencyConvertorService;
import by.x1ss.ModsenPractice.entity.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/exchangeRates")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final CurrencyConvertorService currencyConvertorService;

    @GetMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public List<ExchangeRate> getCurrencies(){
        return currencyConvertorService.getAllExchangeRates();
    }
}
