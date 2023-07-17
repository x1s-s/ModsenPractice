package by.x1ss.ModsenPractice.exception;

public class ResponseParseException extends ServiceBaseException {
    public ResponseParseException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Bank API response parse exception :" + super.getMessage();
    }
}
