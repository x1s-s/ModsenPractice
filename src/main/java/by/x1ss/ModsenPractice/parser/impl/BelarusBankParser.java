package by.x1ss.ModsenPractice.parser.impl;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.exception.ResponseParseException;
import by.x1ss.ModsenPractice.parser.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BelarusBankParser implements Parser {
    private final ObjectMapper objectMapper;

    @Override
    public List<ExchangeRateDto> parse(String input) {
        JsonNode jsonNode;
        try {
            input = input.substring(input.indexOf("{"), input.lastIndexOf("}") + 1);
            log.info("Parsing input: {}", input);
            jsonNode = objectMapper.readTree(input);
            List<ExchangeRateDto> exchangeRateDtos = new ArrayList<>();
            exchangeRateDtos.add(
                    ExchangeRateDto.builder()
                            .baseCurrency("USD")
                            .exchangeCurrency("BYN")
                            .exchangeRate(new BigDecimal(jsonNode.get("USD_in").asText().replace("\"", "")))
                            .build()
            );
            exchangeRateDtos.add(
                    ExchangeRateDto.builder()
                            .baseCurrency("BYN")
                            .exchangeCurrency("USD")
                            .exchangeRate(BigDecimal.ONE
                                    .divide(
                                            new BigDecimal(jsonNode.get("USD_out").asText().replace("\"", "")),
                                            10, RoundingMode.FLOOR))
                            .build()
            );
            exchangeRateDtos.add(
                    ExchangeRateDto.builder()
                            .baseCurrency("EUR")
                            .exchangeCurrency("BYN")
                            .exchangeRate(new BigDecimal(jsonNode.get("EUR_in").asText().replace("\"", "")))
                            .build()
            );
            exchangeRateDtos.add(
                    ExchangeRateDto.builder()
                            .baseCurrency("BYN")
                            .exchangeCurrency("EUR")
                            .exchangeRate(BigDecimal.ONE
                                    .divide(
                                            new BigDecimal(jsonNode.get("EUR_out").asText().replace("\"", "")),
                                            10, RoundingMode.FLOOR))
                            .build()
            );
            exchangeRateDtos.add(
                    ExchangeRateDto.builder()
                            .baseCurrency("USD")
                            .exchangeCurrency("EUR")
                            .exchangeRate(new BigDecimal(jsonNode.get("USD_EUR_in").asText().replace("\"", "")))
                            .build()
            );
            exchangeRateDtos.add(
                    ExchangeRateDto.builder()
                            .baseCurrency("EUR")
                            .exchangeCurrency("USD")
                            .exchangeRate(BigDecimal.ONE.divide(
                                    new BigDecimal(jsonNode.get("USD_EUR_out").asText().replace("\"", "")),
                                    10, RoundingMode.FLOOR))
                            .build()
            );
            log.info("Parsed exchange rates: {}", exchangeRateDtos);
            return exchangeRateDtos;
        } catch (Exception e) {
            throw new ResponseParseException(input);
        }
    }
}
