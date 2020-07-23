package pl.filewicz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("User with name " + message + " not found - Make sure provided login is correct! ");
    }

    public UserNotFoundException() {
    }
}
