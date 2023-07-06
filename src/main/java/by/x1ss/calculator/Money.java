package by.x1ss.calculator;

public enum Money {
    USD(true, '$', "toDollars"),
    RUB(false, 'р', "toRubles");

    public final boolean startBySymbol;
    public final char symbol;
    public final String convertCommand;

    Money(boolean startBySymbol, char symbol, String convertCommand) {
        this.startBySymbol = startBySymbol;
        this.symbol = symbol;
        this.convertCommand = convertCommand;
    }
}
