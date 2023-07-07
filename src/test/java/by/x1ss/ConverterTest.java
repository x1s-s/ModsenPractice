package by.x1ss;

import by.x1ss.convetror.CurrencyConvertor;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ConverterTest {
    private CurrencyConvertor currencyConvertor;

    @Before
    public void setUp() throws Exception {
        currencyConvertor = new CurrencyConvertor();
        currencyConvertor.loadExchangeRates("exchange_rates_test.csv");
    }

    @Test
    public void testGetExchangeRate(){
        assertEquals(BigDecimal.TWO, currencyConvertor.getExchangeRate("USD", "RUB"));
        assertEquals(BigDecimal.valueOf(0.5), currencyConvertor.getExchangeRate("RUB","USD"));
        assertThrows(IllegalArgumentException.class, () -> currencyConvertor.getExchangeRate("USD", "EUR"));
    }

    @Test
    public void testConvert(){
        assertEquals(BigDecimal.valueOf(200), currencyConvertor.convert("USD", "RUB", BigDecimal.valueOf(100)));
        assertEquals(BigDecimal.valueOf(50.0), currencyConvertor.convert("RUB", "USD", BigDecimal.valueOf(100)));
        assertEquals(BigDecimal.valueOf(-200), currencyConvertor.convert("USD", "RUB", BigDecimal.valueOf(-100)));
        assertThrows(IllegalArgumentException.class, () -> currencyConvertor.convert("USD", "EUR", BigDecimal.valueOf(100)));
    }
}
