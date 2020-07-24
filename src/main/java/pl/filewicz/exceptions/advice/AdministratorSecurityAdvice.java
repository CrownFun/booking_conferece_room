package pl.filewicz.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.filewicz.exceptions.AdministratorSecurityException;

@RestControllerAdvice
public class AdministratorSecurityAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.LOCKED)
    public String administratorSecurityHandler(AdministratorSecurityException e) {
        return e.getMessage();
    }
}
