package pl.filewicz.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.filewicz.exceptions.RoomAvailabilityException;

@RestControllerAdvice
public class RoomAvailabilityAdvance {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String roomAvailibilityHandler(RoomAvailabilityException e) {
        return e.getMessage();
    }

    public RoomAvailabilityAdvance() {
    }
}
