package by.x1ss.ModsenPractice.parser;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;

import java.util.List;

public interface Parser {
    List<ExchangeRateDto> parse(String input);
}
