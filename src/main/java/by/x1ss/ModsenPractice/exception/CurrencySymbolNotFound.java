package by.x1ss.ModsenPractice.exception;

public class CurrencySymbolNotFound extends ServiceBaseException{
    public CurrencySymbolNotFound(String expression) {
        super(expression);
    }

    @Override
    public String getMessage() {
        return "Can't find currency symbol of expression : " + super.getMessage();
    }
}
