package by.x1ss.ModsenPractice.service.impl;

import by.x1ss.ModsenPractice.bannkclient.BankClient;
import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.parser.Parser;
import by.x1ss.ModsenPractice.service.ExchangeRateGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateGetServiceImpl implements ExchangeRateGetService {
    private final BankClient bankClient;
    private final Parser parser;

    @Cacheable("exchangeRates")
    public List<ExchangeRateDto> getExchangeRates() {
        return parser.parse(bankClient.getExchangeRate());
    }
}
