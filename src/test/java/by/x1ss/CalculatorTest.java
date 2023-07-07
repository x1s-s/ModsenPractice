package by.x1ss;

import by.x1ss.calculator.Calculator;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.UnknownServiceException;

import static org.junit.Assert.*;

public class CalculatorTest {
    private Calculator calculator;

    @Before
    public void setUp() throws FileNotFoundException {
        calculator = new Calculator("exchange_rates_test.csv");
    }

    @Test
    public void testCalculationTwo() throws UnknownServiceException {
        assertEquals("200р", calculator.calculateTwo("100р+100р"));
        assertEquals("$200", calculator.calculateTwo("$100+$100"));
        assertThrows(NumberFormatException.class, () -> calculator.calculateTwo("100р+100$"));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateTwo("100р*100р"));
    }

    @Test
    public void testCalculationExchange() throws UnknownServiceException{
        assertEquals("$50.0", calculator.calculate("toDollars(100р)"));
        assertEquals("200р", calculator.calculate("toRubles($100)"));
        assertEquals("100.0р", calculator.calculate("toRubles(toDollars(100р))"));
        assertEquals("$100.0", calculator.calculate("toDollars(toRubles($100))"));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("toRubles(100x)"));
    }

    @Test
    public void testCalculation() throws UnknownServiceException{
        assertEquals("600р", calculator.calculate("100р+200р+300р"));
        assertEquals("$450.0", calculator.calculate("toDollars(100р+200р)+$300"));
        assertEquals("-100р", calculator.calculate("100р-200р"));
        assertEquals("$-50.0", calculator.calculate("toDollars(100р-200р)"));
        assertEquals("600р", calculator.calculate("toRubles($100 + $100 + $100)"));
        assertEquals("$-100.0", calculator.calculate("toDollars(100р-300р)"));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("100р*200р"));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate("100р+200$"));
    }
}
