package by.x1ss.ModsenPractice.calculator;

public enum Money {
    USD(true, '$', "toDollars", 431),
    RUB(false, 'р', "toRubles", 456),
    EUR(true, '€', "toEuros", 451);

    public final boolean startBySymbol;
    public final char symbol;
    public final String convertCommand;
    public final int id;

    Money(boolean startBySymbol, char symbol, String convertCommand, int id) {
        this.startBySymbol = startBySymbol;
        this.symbol = symbol;
        this.convertCommand = convertCommand;
        this.id = id;
    }
}
