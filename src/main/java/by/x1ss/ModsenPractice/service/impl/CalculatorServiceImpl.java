package by.x1ss.ModsenPractice.service.impl;

import by.x1ss.ModsenPractice.exception.CurrencySymbolNotFound;
import by.x1ss.ModsenPractice.exception.IllegalOperation;
import by.x1ss.ModsenPractice.service.CalculatorService;
import by.x1ss.ModsenPractice.service.utils.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.x1ss.ModsenPractice.service.utils.Utils.parseToBigDecimal;
import static by.x1ss.ModsenPractice.service.utils.Utils.roundCurrency;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {
    private final CurrencyConvertorServiceImpl currencyConvertorService;

    public String calculate(String expression) {
        return roundCurrency(calculateExpression(expression.replace(" ", "")));
    }

    private String calculateExpression(String expression) {
        String startExpression = expression;
        while (expression.contains("(")) {
            int start = expression.lastIndexOf("(");
            int end = expression.indexOf(")", start);
            String command = "";
            String subExpression = expression.substring(start + 1, end);

            for (var money : Money.values()) {
                if (expression.startsWith(money.convertCommand, start - money.convertCommand.length())) {
                    command = money.convertCommand;
                    break;
                }
            }

            String result;
            if (Pattern.compile(".+[-+].+").matcher(subExpression).find(1)) {
                result = calculateExpression(subExpression);
                if (result.startsWith("-")) {
                    expression = expression.replace(subExpression, result);
                } else {
                    expression = expression.replace(command + "(" + subExpression + ")", command + "(" + result + ")");
                }
            } else {
                result = calculateExpression(command + subExpression);
                if (!command.equals("")) {
                    expression = expression.substring(0, start - command.length()) + result + expression.substring(end + 1);
                }
            }
        }

        for (var exchangeTo : Money.values()) {
            if (expression.startsWith(exchangeTo.convertCommand)) {
                expression = expression.substring(exchangeTo.convertCommand.length());
                Matcher matcher = Pattern.compile("[^-+\\d.]").matcher(expression);
                if (matcher.find()) {
                    char symbol = expression.charAt(matcher.start());
                    expression = expression.replace(String.valueOf(symbol), "");
                    boolean checker = false;
                    for (var base : Money.values()) {
                        if (symbol == base.symbol) {
                            checker = true;
                            if (exchangeTo.startBySymbol) {
                                return exchangeTo.symbol + currencyConvertorService.convert(base.name(), exchangeTo.name(), parseToBigDecimal(expression)).toString();
                            } else {
                                return currencyConvertorService.convert(base.name(), exchangeTo.name(), parseToBigDecimal(expression)).toString() + exchangeTo.symbol;
                            }
                        }
                    }
                    if (!checker) {
                        throw new CurrencySymbolNotFound(expression);
                    }
                } else {
                    throw new CurrencySymbolNotFound(expression);
                }
            }
        }

        if (Pattern.compile("[-+]").matcher(Objects.requireNonNull(expression)).find(2)){
            Matcher symbolMatcher = Pattern.compile("[^-+\\d.]").matcher(expression);
            if (symbolMatcher.find()) {
                int currencySymbolIndex = symbolMatcher.start();
                boolean startBySymbol = currencySymbolIndex == 0;
                char symbol = expression.charAt(currencySymbolIndex);
                expression = expression.replace(String.valueOf(symbol), "");
                expression = expression.replace("--", "+");
                expression = expression.replace("+-", "-");
                Matcher matcher = Pattern.compile("[-+]").matcher(expression);
                int previousOperation = 0;
                BigDecimal result = BigDecimal.ZERO;
                while (matcher.find(previousOperation + 1)){
                    int currentOperation = matcher.start();
                    result = result.add(new BigDecimal(expression.substring(previousOperation, currentOperation)));
                    previousOperation = currentOperation;
                }
                result = result.add(new BigDecimal(expression.substring(previousOperation)));
                if(startBySymbol){
                    return symbol + result.toString();
                } else {
                    return result.toString() + symbol;
                }
            } else {
                throw new CurrencySymbolNotFound(expression);
            }
        }

        if (startExpression.equals(expression)) {
            throw new IllegalOperation();
        }

        return expression;
    }
}
