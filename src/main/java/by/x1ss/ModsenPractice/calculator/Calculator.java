package by.x1ss.ModsenPractice.calculator;

import by.x1ss.ModsenPractice.service.CurrencyConvertorService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.net.UnknownServiceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class Calculator {
    private final CurrencyConvertorService currencyConvertorService;

    public String calculate(String expression) throws UnknownServiceException{
        expression = expression.replaceAll(" ", "");
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
            if (Pattern.compile("^.+[-+].+$").matcher(subExpression).find()) {
                result = calculate(subExpression);
                if (result.startsWith("-")) {
                    expression = expression.replace("(" + subExpression + ")", result);
                } else {
                    expression = expression.replace(command + "(" + subExpression + ")", command + "(" + result + ")");
                }
            } else {
                result = calculate(command + subExpression);
                if (!command.equals("")) {
                    expression = expression.substring(0, start - command.length()) + result + expression.substring(end + 1);
                }
            }

        }

        for (var exchangeTo : Money.values()) {
            if (expression.startsWith(exchangeTo.convertCommand)) {
                expression = expression.substring(exchangeTo.convertCommand.length());
                Matcher matcher = Pattern.compile("[^-+\\d]").matcher(expression);
                if (matcher.find()) {
                    char symbol = expression.charAt(matcher.start());
                    expression = expression.replace(String.valueOf(symbol), "");
                    boolean checker = false;
                    for (var base : Money.values()) {
                        if (symbol == base.symbol) {
                            checker = true;
                            if (exchangeTo.startBySymbol) {
                                return exchangeTo.symbol + currencyConvertorService.convert(base.name(), exchangeTo.name(), new BigDecimal(expression)).toString();
                            } else {
                                return currencyConvertorService.convert(base.name(), exchangeTo.name(), new BigDecimal(expression)).toString() + exchangeTo.symbol;
                            }
                        }
                    }
                    if (!checker) {
                        throw new IllegalArgumentException("Can't find currency symbol: " + symbol);
                    }
                } else {
                    throw new IllegalArgumentException("Can't find currency symbol: " + expression);
                }
            }
        }


        while (true) {
            Matcher matcher = Pattern.compile("[-+]").matcher(expression);
            if (!matcher.find(1)) {
                break;
            }
            int firstOperation = matcher.start();
            String subExpression = expression.substring(0, firstOperation);
            if (matcher.find(firstOperation + 1)) {
                int secondOperation = matcher.start();
                if (secondOperation - 1 == firstOperation) {
                    if (expression.charAt(secondOperation) == '-' && expression.charAt(secondOperation + 1) == '-') {
                        expression = expression.replaceFirst("--", "+");
                    } else {
                        expression = expression.replaceFirst("\\+-", "-");
                    }
                    continue;
                }
                subExpression += expression.charAt(firstOperation) + expression.substring(firstOperation + 1, secondOperation);
                expression = expression.replace(subExpression, calculateTwo(subExpression));
            } else {
                expression = calculateTwo(expression);
            }
        }

        if(startExpression.equals(expression)){
            throw new IllegalArgumentException("Can't find operation");
        }

        return expression;
    }

    public String calculateTwo(String expression) throws UnknownServiceException{
        Matcher matcher = Pattern.compile("[^-+\\d]").matcher(expression);
        if (matcher.find()) {
            int currencySymbolIndex = matcher.start();
            boolean startBySymbol = currencySymbolIndex == 0;
            char symbol = expression.charAt(currencySymbolIndex);
            if (expression.contains("+")) {
                expression = expression.replace(String.valueOf(symbol), "");
                BigDecimal first = new BigDecimal(expression.substring(0, expression.indexOf("+")));
                BigDecimal second = new BigDecimal(expression.substring(expression.indexOf("+") + 1));
                if (startBySymbol) {
                    return symbol + first.add(second).toString();
                } else {
                    return first.add(second).toString() + symbol;
                }
            } else if (expression.contains("-")) {
                expression = expression.replace(String.valueOf(symbol), "");
                int indexOfOperation = expression.substring(1).indexOf("-") + 1;
                BigDecimal first = new BigDecimal(expression.substring(0, indexOfOperation));
                BigDecimal second = new BigDecimal(expression.substring(indexOfOperation + 1));
                if (startBySymbol) {
                    return symbol + first.subtract(second).toString();
                } else {
                    return first.subtract(second).toString() + symbol;
                }
            } else {
                throw new IllegalArgumentException("Can't find operation");
            }
        }
        throw new UnknownServiceException();
    }
}
