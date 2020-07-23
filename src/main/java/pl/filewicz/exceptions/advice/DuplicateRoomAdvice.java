package pl.filewicz.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.filewicz.exceptions.DuplicateRoomException;

@RestControllerAdvice
public class DuplicateRoomAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String duplicateRoomHandler(DuplicateRoomException e) {
        return e.getMessage();
    }
}
