package by.x1ss.ModsenPractice;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.service.CalculatorService;
import by.x1ss.ModsenPractice.service.ExchangeRateGetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest extends AbstractTest{
    @Autowired
    private CalculatorService calculatorService;

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
    public void calculateSimpleStartBySymbolExpressionTest(){
        assertEquals("$2.00", calculatorService.calculate("$1 + $1"));
        assertEquals("$3.00", calculatorService.calculate("$1 + $1 + $1"));
        assertEquals("$1.00", calculatorService.calculate("$1 + $1 - $1"));
        assertEquals("$2.00", calculatorService.calculate("$1 - -$1"));
        assertEquals("$0.00", calculatorService.calculate("$1 - $1"));
        assertEquals("$0.00", calculatorService.calculate("$-1 + $1"));
        assertEquals("$-2.00", calculatorService.calculate("$-1 - $1"));
        assertEquals("$-2.00", calculatorService.calculate("$-1 + $-1"));
    }

    @Test
    public void calculateSimpleEndBySymbolExpressionTest(){
        assertEquals("2.00р", calculatorService.calculate("1р + 1р"));
        assertEquals("3.00р", calculatorService.calculate("1р + 1р + 1р"));
        assertEquals("1.00р", calculatorService.calculate("1р + 1р - 1р"));
        assertEquals("2.00р", calculatorService.calculate("1р - -1р"));
        assertEquals("0.00р", calculatorService.calculate("1р - 1р"));
        assertEquals("0.00р", calculatorService.calculate("-1р + 1р"));
        assertEquals("-2.00р", calculatorService.calculate("-1р - 1р"));
        assertEquals("-2.00р", calculatorService.calculate("-1р + -1р"));
    }

    @Test
    public void calculationHardExpressionTest(){
        assertEquals("$1.00", calculatorService.calculate("toDollars(1р + 1р)"));
        assertEquals("$-1.00", calculatorService.calculate("toDollars(-1р + -1р)"));
        assertEquals("$2.00", calculatorService.calculate("toDollars(1р + 1р) + $1"));
        assertEquals("€10.00", calculatorService.calculate("toEuros(toDollars(1р + 1р) + $1)"));
        assertEquals("€11.00", calculatorService.calculate("toEuros(toDollars(1р + 1р) + $1) + €1"));
        assertEquals("-9900199.00р", calculatorService.calculate("toRubles(toDollars(toRubles(toEuros(100000р + 100р - 200р - 9999999р)) + 100р - 200р))"));
    }
}
