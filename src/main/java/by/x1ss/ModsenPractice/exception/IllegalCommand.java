package by.x1ss.ModsenPractice.exception;

public class IllegalCommand extends ServiceBaseException {
    public IllegalCommand(String command) {
        super(command);
    }

    @Override
    public String getMessage() {
        return "Illegal command : " + super.getMessage();
    }
}
