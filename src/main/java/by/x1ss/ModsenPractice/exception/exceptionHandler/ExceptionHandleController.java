package by.x1ss.ModsenPractice.exception.exceptionHandler;

import by.x1ss.ModsenPractice.exception.CurrencySymbolNotFound;
import by.x1ss.ModsenPractice.exception.ExchangeRateNotFound;
import by.x1ss.ModsenPractice.exception.IllegalOperation;
import by.x1ss.ModsenPractice.exception.ResponseParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExchangeRateNotFound.class)
    public ResponseEntity<String> handleExchangeRateNotFound(ExchangeRateNotFound e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CurrencySymbolNotFound.class)
    public ResponseEntity<String> handleExchangeRateNotFound(CurrencySymbolNotFound e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalOperation.class)
    public ResponseEntity<String> handleExchangeRateNotFound(IllegalOperation e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ResponseParseException.class)
    public ResponseEntity<String> handleResponseParseException(ResponseParseException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
