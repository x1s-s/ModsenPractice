package by.x1ss.ModsenPractice.service.utils;

import by.x1ss.ModsenPractice.exception.IllegalOperation;

import java.math.BigDecimal;

public class Utils {
    public static BigDecimal parseToBigDecimal(String input) {
        try {
            return new BigDecimal(input);
        } catch (NumberFormatException e) {
            throw new IllegalOperation(e.getMessage().charAt(10));
        }
    }
}
