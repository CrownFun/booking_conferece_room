package pl.filewicz.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.filewicz.exceptions.DuplicateUserException;

@RestControllerAdvice
public class DuplicateUserAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String duplicateUserHandler(DuplicateUserException e){
        return e.getMessage();
    }
}
