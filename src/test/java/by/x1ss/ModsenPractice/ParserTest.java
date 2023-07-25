package by.x1ss.ModsenPractice;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.exception.ResponseParseException;
import by.x1ss.ModsenPractice.parser.Parser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest extends AbstractTest {
    @Autowired
    private Parser parser;

    @Test
    public void parseTest() throws FileNotFoundException {
        String input = new Scanner(new File("src/test/resources/response.txt")).nextLine();
        List<ExchangeRateDto> parseList = parser.parse(input);
        List<ExchangeRateDto> correctlist = List.of(
                new ExchangeRateDto("USD", "BYN", new BigDecimal("2.9800")),
                new ExchangeRateDto("BYN", "USD", new BigDecimal("0.3300330033")),
                new ExchangeRateDto("EUR", "BYN", new BigDecimal("3.2800")),
                new ExchangeRateDto("BYN", "EUR", new BigDecimal("0.2958579881")),
                new ExchangeRateDto("USD", "EUR", new BigDecimal("0.8870")),
                new ExchangeRateDto("EUR", "USD", new BigDecimal("0.8992805755"))
        );
        assertEquals(correctlist, parseList);
    }

    @Test
    public void parseExceptionTest() {
        assertThrows(ResponseParseException.class, () -> parser.parse("[]"));
        assertThrows(ResponseParseException.class, () -> parser.parse("[{\"hello\":\"world\"}]"));
    }
}
