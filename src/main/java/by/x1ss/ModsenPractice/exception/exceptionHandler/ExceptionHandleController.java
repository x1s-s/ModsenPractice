package by.x1ss.ModsenPractice.exception.exceptionHandler;

import by.x1ss.ModsenPractice.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceBaseException.class)
    public ResponseEntity<String> handleExchangeRateNotFound(ServiceBaseException e){
        log.warn(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
