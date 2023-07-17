package by.x1ss.ModsenPractice;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.exception.CurrencySymbolNotFound;
import by.x1ss.ModsenPractice.exception.ExchangeRateNotFound;
import by.x1ss.ModsenPractice.exception.IllegalCommand;
import by.x1ss.ModsenPractice.service.CurrencyConvertorService;
import by.x1ss.ModsenPractice.service.ExchangeRateGetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    public void convertTest(){
        assertEquals(BigDecimal.valueOf(5), currencyConvertorService.convert("USD", "EUR", BigDecimal.ONE));
        assertEquals(BigDecimal.valueOf(1.0), currencyConvertorService.convert("EUR", "USD", BigDecimal.valueOf(5)));
        assertEquals(BigDecimal.valueOf(2), currencyConvertorService.convert("USD", "BYN", BigDecimal.ONE));
        assertEquals(BigDecimal.valueOf(1.5), currencyConvertorService.convert("BYN", "USD", BigDecimal.valueOf(3)));
        assertEquals(BigDecimal.valueOf(4), currencyConvertorService.convert("EUR", "BYN", BigDecimal.ONE));
        assertEquals(new BigDecimal("1.00"), currencyConvertorService.convert("BYN", "EUR", BigDecimal.valueOf(4)));
        assertThrows(ExchangeRateNotFound.class , () -> currencyConvertorService.convert("EUR", "RUB", BigDecimal.ONE));
    }

    @Test
    public void convertByCommandTest(){
        assertEquals("$0.2", currencyConvertorService.convertByCommand("toDollars", "€1"));
        assertEquals("€25", currencyConvertorService.convertByCommand("toEuros", "$5"));
        assertEquals("$0.5", currencyConvertorService.convertByCommand("toDollars", "1р"));
        assertEquals("6р", currencyConvertorService.convertByCommand("toRubles", "$3"));
        assertEquals("€0.25", currencyConvertorService.convertByCommand("toEuros", "1р"));
        assertEquals("16р", currencyConvertorService.convertByCommand("toRubles", "€4"));
        assertThrows(IllegalCommand.class , () -> currencyConvertorService.convertByCommand("toRUB", "€1"));
        assertThrows(CurrencySymbolNotFound.class , () -> currencyConvertorService.convertByCommand("toDollars", "1"));
        assertThrows(CurrencySymbolNotFound.class , () -> currencyConvertorService.convertByCommand("toDollars", "1a"));
    }


}

