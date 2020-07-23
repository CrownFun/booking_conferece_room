package pl.filewicz.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.filewicz.exceptions.RoomNotFoundException;

@RestControllerAdvice
public class RoomNotFoundAdvice {

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String roomNotFoundHandler(RoomNotFoundException e) {
        return e.getMessage();
    }
}
