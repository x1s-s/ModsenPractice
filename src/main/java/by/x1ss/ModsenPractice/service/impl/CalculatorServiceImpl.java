package by.x1ss.ModsenPractice.service.impl;

import by.x1ss.ModsenPractice.dto.Money;
import by.x1ss.ModsenPractice.exception.CurrencySymbolNotFound;
import by.x1ss.ModsenPractice.exception.IllegalOperation;
import by.x1ss.ModsenPractice.exception.IncorrectNumberOfBrackets;
import by.x1ss.ModsenPractice.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.x1ss.ModsenPractice.service.utils.Utils.roundCurrency;
import static by.x1ss.ModsenPractice.service.utils.Utils.validateBrackets;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {
    private final CurrencyConvertorServiceImpl currencyConvertorService;

    public String calculate(String expression) {
        if(!validateBrackets(expression)){
            throw new IncorrectNumberOfBrackets(expression);
        }
        return roundCurrency(calculateExpression(expression.replace(" ", "")));
    }

    private String calculateExpression(String expression) {
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
                if (!command.equals("")) {
                    result = currencyConvertorService.convertByCommand(command, subExpression);
                    expression = expression.substring(0, start - command.length()) + result + expression.substring(end + 1);
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
                    try{
                        result = result.add(new BigDecimal(expression.substring(previousOperation, currentOperation)));
                    } catch (NumberFormatException e){
                        throw new IllegalOperation(e.getMessage().charAt(10));
                    }
                    previousOperation = currentOperation;
                }
                try {
                    result = result.add(new BigDecimal(expression.substring(previousOperation)));
                } catch (NumberFormatException e) {
                    throw new IllegalOperation(e.getMessage().charAt(10));
                }
                if(startBySymbol){
                    return symbol + result.toString();
                } else {
                    return result.toString() + symbol;
                }
            } else {
                throw new CurrencySymbolNotFound(expression);
            }
        }

        return expression;
    }
}
