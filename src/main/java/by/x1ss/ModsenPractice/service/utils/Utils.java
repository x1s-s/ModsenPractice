package by.x1ss.ModsenPractice.service.utils;

import by.x1ss.ModsenPractice.exception.CurrencySymbolNotFound;
import by.x1ss.ModsenPractice.exception.IllegalOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String roundCurrency(String expression) {
        Matcher matcher = Pattern.compile("[^-+\\d.]").matcher(expression);
        if (matcher.find()) {
            int currencySymbolIndex = matcher.start();
            boolean startBySymbol = currencySymbolIndex == 0;
            char symbol = expression.charAt(currencySymbolIndex);
            expression = expression.replace(String.valueOf(symbol), "");
            String value = parseToBigDecimal(expression).setScale(2, RoundingMode.HALF_UP).toString();
            if (startBySymbol) {
                return symbol + value;
            } else {
                return value + symbol;
            }
        } else {
            throw new CurrencySymbolNotFound(expression);
        }
    }

    public static BigDecimal parseToBigDecimal(String input) {
        try {
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            throw new IllegalOperation(e.getMessage().charAt(10));
        }
    }
}
