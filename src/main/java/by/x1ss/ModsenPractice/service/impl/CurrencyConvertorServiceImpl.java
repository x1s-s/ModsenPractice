package by.x1ss.ModsenPractice.service.impl;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.exception.CurrencySymbolNotFound;
import by.x1ss.ModsenPractice.exception.ExchangeRateNotFound;
import by.x1ss.ModsenPractice.exception.IllegalCommand;
import by.x1ss.ModsenPractice.service.CurrencyConvertorService;
import by.x1ss.ModsenPractice.service.ExchangeRateGetService;
import by.x1ss.ModsenPractice.service.utils.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.x1ss.ModsenPractice.service.utils.Utils.parseToBigDecimal;


@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyConvertorServiceImpl implements CurrencyConvertorService {
    private final ExchangeRateGetService exchangeRateGetService;

    @Override
    public BigDecimal convert(String baseCurrency, String exchangeCurrency, BigDecimal amount) {
        return amount.multiply(getExchangeRate(baseCurrency, exchangeCurrency));
    }

    @Override
    public BigDecimal getExchangeRate(String baseCurrency, String exchangeCurrency) {
        List<ExchangeRateDto> exchangeRates = exchangeRateGetService.getExchangeRates();
        for (ExchangeRateDto exchangeRate : exchangeRates) {
            if (exchangeRate.getBaseCurrency().equals(baseCurrency) && exchangeRate.getExchangeCurrency().equals(exchangeCurrency)) {
                return exchangeRate.getExchangeRate();
            }
        }
        throw new ExchangeRateNotFound(baseCurrency, exchangeCurrency);
    }

    @Override
    public String convertByCommand(String command, String amount) {
        Matcher matcher = Pattern.compile("[^-+\\d.]").matcher(amount);
        if (matcher.find()) {
            char baseCurrencySymbol = amount.charAt(matcher.start());
            String baseCurrency = null;
            Money exchangeCurrency = null;
            for (var money : Money.values()) {
                if (money.symbol == baseCurrencySymbol) {
                    baseCurrency = money.name();
                }
                if (money.convertCommand.equals(command)) {
                    exchangeCurrency = money;
                }
            }
            if (exchangeCurrency == null) {
                throw new IllegalCommand(command);
            }
            if (baseCurrency == null) {
                throw new CurrencySymbolNotFound(command + '(' + amount + ')');
            }
            if (exchangeCurrency.startBySymbol) {
                return exchangeCurrency.symbol + convert(baseCurrency, exchangeCurrency.name(), parseToBigDecimal(amount.replace(String.valueOf(baseCurrencySymbol), ""))).toString();
            } else {
                return convert(baseCurrency, exchangeCurrency.name(), parseToBigDecimal(amount.replace(String.valueOf(baseCurrencySymbol), ""))).toString() + exchangeCurrency.symbol;
            }
        } else {
            throw new CurrencySymbolNotFound(command + '(' + amount + ')');
        }
    }
}
