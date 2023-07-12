package by.x1ss.ModsenPractice.exception;

public class IllegalOperation extends RuntimeException{
    @Override
    public String getMessage() {
        return "Can't find operation or operation is incorrect";
    }
}
