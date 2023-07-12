package by.x1ss.ModsenPractice.service;

import by.x1ss.ModsenPractice.exception.ExchangeRateNotFound;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;


@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyConvertorService {
    private final ObjectMapper objectMapper;

    public BigDecimal convert(String baseCurrency, String exchangeCurrency, BigDecimal amount) {
        BigDecimal exchangeRate = getExchangeRate(baseCurrency, exchangeCurrency);
        return amount.multiply(exchangeRate);
    }

    public BigDecimal getExchangeRate(String baseCurrency, String exchangeCurrency) {
        try {
            if ("BYN".equals(baseCurrency)) {
                JsonNode jsonNode = objectMapper.readTree(URI.create("https://api.nbrb.by/exrates/rates/" + exchangeCurrency + "?parammode=2").toURL());
                BigDecimal curScale = new BigDecimal(jsonNode.get("Cur_Scale").asText());
                BigDecimal curRate = new BigDecimal(jsonNode.get("Cur_OfficialRate").asText());
                return BigDecimal.valueOf(1).divide(curRate.divide(curScale, 10, RoundingMode.FLOOR), 10, RoundingMode.FLOOR);
            } else if ("BYN".equals(exchangeCurrency)) {
                JsonNode jsonNode = objectMapper.readTree(URI.create("https://api.nbrb.by/exrates/rates/" + baseCurrency + "?parammode=2").toURL());
                BigDecimal curScale = new BigDecimal(jsonNode.get("Cur_Scale").asText());
                BigDecimal curRate = new BigDecimal(jsonNode.get("Cur_OfficialRate").asText());
                return curRate.divide(curScale, 10, RoundingMode.FLOOR);
            } else {
                JsonNode jsonNodeBaseCurrency = objectMapper.readTree(URI.create("https://api.nbrb.by/exrates/rates/" + baseCurrency + "?parammode=2").toURL());
                JsonNode jsonNodeExchangeCurrency = objectMapper.readTree(URI.create("https://api.nbrb.by/exrates/rates/" + exchangeCurrency + "?parammode=2").toURL());
                BigDecimal baseCurrencyRate = new BigDecimal(jsonNodeBaseCurrency.get("Cur_OfficialRate").asText());
                BigDecimal baseCurrencyScale = new BigDecimal(jsonNodeBaseCurrency.get("Cur_Scale").asText());
                BigDecimal exchangeCurrencyRate = new BigDecimal(jsonNodeExchangeCurrency.get("Cur_OfficialRate").asText());
                BigDecimal exchangeCurrencyScale = new BigDecimal(jsonNodeExchangeCurrency.get("Cur_Scale").asText());

                return exchangeCurrencyRate.multiply(baseCurrencyScale).divide(exchangeCurrencyScale.multiply(baseCurrencyRate), 10, RoundingMode.FLOOR);
            }
        } catch (IOException e) {
            throw new ExchangeRateNotFound(baseCurrency, exchangeCurrency);
        }
    }
}
