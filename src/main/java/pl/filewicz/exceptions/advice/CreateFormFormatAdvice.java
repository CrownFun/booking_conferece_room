package pl.filewicz.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.filewicz.exceptions.CreateFormFormatException;

@RestControllerAdvice
public class CreateFormFormatAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createFormFormatHandler(CreateFormFormatException e){
        return e.getMessage();
    }

}
