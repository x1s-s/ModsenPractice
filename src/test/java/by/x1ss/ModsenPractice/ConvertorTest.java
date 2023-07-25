package by.x1ss.ModsenPractice;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.exception.CurrencySymbolNotFound;
import by.x1ss.ModsenPractice.exception.IllegalCommand;
import by.x1ss.ModsenPractice.service.CurrencyConvertorService;
import by.x1ss.ModsenPractice.service.ExchangeRateGetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ConvertorTest extends AbstractTest{
    @Autowired
    private CurrencyConvertorService currencyConvertorService;
    @MockBean
    private ExchangeRateGetService exchangeRateGetService;

    @BeforeEach
    public void setUp(){
        Mockito.when(exchangeRateGetService.getExchangeRates()).thenReturn(
                List.of(
                        new ExchangeRateDto("USD", "BYN", BigDecimal.valueOf(2)),
                        new ExchangeRateDto("BYN", "USD", BigDecimal.valueOf(0.5)),
                        new ExchangeRateDto("EUR", "BYN", BigDecimal.valueOf(4)),
                        new ExchangeRateDto("BYN", "EUR", BigDecimal.valueOf(0.25)),
                        new ExchangeRateDto("USD", "EUR", BigDecimal.valueOf(5)),
                        new ExchangeRateDto("EUR", "USD", BigDecimal.valueOf(0.2))
                )
        );
    }

    @ParameterizedTest
    @CsvSource({
            "USD, EUR, 1, 5",
            "EUR, USD, 5, 1.0",
            "USD, BYN, 1, 2",
            "BYN, USD, 3, 1.5",
            "EUR, BYN, 1, 4",
            "BYN, EUR, 4, 1.00"
    })
    public void convertTest(String from, String to, BigDecimal amount, BigDecimal expect){
        assertEquals(expect, currencyConvertorService.convert(from, to, amount));
    }

    @ParameterizedTest
    @CsvSource({
            "toDollars, €1, $0.2",
            "toEuros, $5, €25",
            "toDollars, 1р, $0.5",
            "toRubles, $3, 6р",
            "toEuros, 1р, €0.25",
            "toRubles, €4, 16р"
    })
    public void convertByCommandTest(String command, String value, String expected){
        assertEquals(expected, currencyConvertorService.convertByCommand(command, value));
    }

    @Test
    public void convertByCommandExceptionTest(){
        assertThrows(IllegalCommand.class , () -> currencyConvertorService.convertByCommand("toRUB", "€1"));
        assertThrows(CurrencySymbolNotFound.class , () -> currencyConvertorService.convertByCommand("toDollars", "1"));
        assertThrows(CurrencySymbolNotFound.class , () -> currencyConvertorService.convertByCommand("toDollars", "1a"));
    }

}

