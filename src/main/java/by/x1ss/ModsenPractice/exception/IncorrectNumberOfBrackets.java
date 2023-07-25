package by.x1ss.ModsenPractice.exception;

public class IncorrectNumberOfBrackets extends ServiceBaseException {
    public IncorrectNumberOfBrackets(String expression) {
        super("Incorrect number of brackets : " + expression);
    }
}
