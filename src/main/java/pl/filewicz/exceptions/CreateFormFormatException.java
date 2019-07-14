package pl.filewicz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CREATED, reason = "Arguments do not follow data restrictions specified in requirements")
public class CreateFormFormatException extends RuntimeException {
}
