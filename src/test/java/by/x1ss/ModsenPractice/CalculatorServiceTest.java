package by.x1ss.ModsenPractice;

import by.x1ss.ModsenPractice.dto.ExchangeRateDto;
import by.x1ss.ModsenPractice.exception.CurrencySymbolNotFound;
import by.x1ss.ModsenPractice.exception.ExchangeRateNotFound;
import by.x1ss.ModsenPractice.exception.IllegalOperation;
import by.x1ss.ModsenPractice.exception.IncorrectNumberOfBrackets;
import by.x1ss.ModsenPractice.service.CalculatorService;
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

public class CalculatorServiceTest extends AbstractTest {
    @Autowired
    private CalculatorService calculatorService;

    @MockBean
    private ExchangeRateGetService exchangeRateGetService;

    @BeforeEach
    public void setUp() {
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
            "$2.00,$1 + $1",
            "$3.00,$1 + $1 + $1",
            "$1.00,$1 + $1 - $1",
            "$2.00,$1 - -$1",
            "$0.00,$1 - $1",
            "$0.00,$-1 + $1",
            "$-2.00,$-1 - $1",
            "$-2.00,$-1 + $-1"
    })
    public void calculateSimpleStartBySymbolExpressionTest(String input, String expect) {
        assertEquals(expect, calculatorService.calculate(input));
    }


    @ParameterizedTest
    @CsvSource({
            "2.00р,1р + 1р",
            "3.00р,1р + 1р + 1р",
            "1.00р,1р + 1р - 1р",
            "2.00р,1р - -1р",
            "0.00р,1р - 1р",
            "0.00р,-1р + 1р",
            "-2.00р,-1р - 1р",
            "-2.00р,-1р + -1р"
    })
    public void calculateSimpleEndBySymbolExpressionTest(String expect, String input) {
        assertEquals(expect, calculatorService.calculate(input));
    }

    @ParameterizedTest
    @CsvSource({
            "$1.00,toDollars(1р + 1р)",
            "$-1.00,toDollars(-1р + -1р)",
            "$2.00,toDollars(1р + 1р) + $1",
            "€10.00,toEuros(toDollars(1р + 1р) + $1)",
            "€11.00,toEuros(toDollars(1р + 1р) + $1) + €1",
            "$453.90,toDollars(737р + toRubles($85.4))",
            "-9900199.00р,toRubles(toDollars(toRubles(toEuros(100000р + 100р - 200р - 9999999р)) + 100р - 200р))"
    })
    public void calculationHardExpressionTest(String expect, String input){
        assertEquals(expect, calculatorService.calculate(input));
    }

    @Test
    public void exceptionTest() {
        assertThrows(IncorrectNumberOfBrackets.class, () -> calculatorService.calculate("toDollars(1р + 1р))"));
        assertThrows(IncorrectNumberOfBrackets.class, () -> calculatorService.calculate("toDollars((1р + 1р)"));
        assertThrows(IllegalOperation.class, () -> calculatorService.calculate("toDollars(1р * 1р)"));
        assertThrows(ExchangeRateNotFound.class, () -> calculatorService.calculate("toDollars(toDollars(1р))"));
        assertThrows(CurrencySymbolNotFound.class, () -> calculatorService.calculate("toDollars(1)"));
        assertThrows(IllegalOperation.class, () -> calculatorService.calculate("$1 + 1р"));
    }
}
