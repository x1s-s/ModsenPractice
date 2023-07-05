package by.x1ss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
@AllArgsConstructor
public class Money {
    private final String name;
    private final char symbol;
    private HashMap<String, BigDecimal> rates;
}
