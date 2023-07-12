package by.x1ss.ModsenPractice.calculator;

public enum Money {
    USD(true, '$', "toDollars"),
    BYN(false, 'р', "toRubles"),
    EUR(true, '€', "toEuros");

    public final boolean startBySymbol;
    public final char symbol;
    public final String convertCommand;

    Money(boolean startBySymbol, char symbol, String convertCommand) {
        this.startBySymbol = startBySymbol;
        this.symbol = symbol;
        this.convertCommand = convertCommand;
    }
}
