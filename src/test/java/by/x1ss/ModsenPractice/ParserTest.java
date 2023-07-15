package by.x1ss.ModsenPractice;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.exception.ResponseParseException;
import by.x1ss.ModsenPractice.parser.Parser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest extends AbstractTest {
    @Autowired
    private Parser parser;

    @Test
    public void parseTest() {
        String input = "[{\"USD_in\":\"2.9800\",\"USD_out\":\"3.0300\",\"EUR_in\":\"3.2800\",\"EUR_out\":\"3.3800\",\"RUB_in\":\"2.8000\",\"RUB_out\":\"3.4500\",\"GBP_in\":\"0.0000\",\"GBP_out\":\"0.0000\",\"CAD_in\":\"0.0000\",\"CAD_out\":\"0.0000\",\"PLN_in\":\"0.0000\",\"PLN_out\":\"0.0000\",\"SEK_in\":\"0.0000\",\"SEK_out\":\"0.0000\",\"CHF_in\":\"0.0000\",\"CHF_out\":\"0.0000\",\"USD_EUR_in\":\"0.8870\",\"USD_EUR_out\":\"1.1120\",\"USD_RUB_in\":\"87.0000\",\"USD_RUB_out\":\"0.0093\",\"RUB_EUR_in\":\"0.0083\",\"RUB_EUR_out\":\"96.0000\",\"JPY_in\":\"0.0000\",\"JPY_out\":\"0.0000\",\"CNY_in\":\"0.0000\",\"CNY_out\":\"0.0000\",\"CNY_EUR_in\":\"0.0000\",\"CNY_EUR_out\":\"0.0000\",\"CNY_USD_in\":\"0.0000\",\"CNY_USD_out\":\"0.0000\",\"CNY_RUB_in\":\"0.0000\",\"CNY_RUB_out\":\"0.0000\",\"CZK_in\":\"0.0000\",\"CZK_out\":\"0.0000\",\"NOK_in\":\"0.0000\",\"NOK_out\":\"0.0000\",\"filial_id\":\"2212\",\"sap_id\":\"50010866\",\"info_worktime\":\"Пн        |Вт        |Ср 09 00 17 00 13 00 14 00|Чт 09 00 17 00 13 00 14 00|Пт 09 00 17 00 13 00 14 00|Сб 09 00 17 00 13 00 14 00|Вс 09 00 17 00 13 00 14 00|\",\"street_type\":\"ул.\",\"street\":\"Ильича\",\"filials_text\":\"Обменный пункт 300\\/3077\",\"home_number\":\"299\",\"name\":\"Гомель\",\"name_type\":\"г.\"}]";
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
