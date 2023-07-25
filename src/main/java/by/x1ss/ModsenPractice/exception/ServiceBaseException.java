package by.x1ss.ModsenPractice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceBaseException extends RuntimeException {
    public ServiceBaseException(String message) {
        super(message);
    }
}
