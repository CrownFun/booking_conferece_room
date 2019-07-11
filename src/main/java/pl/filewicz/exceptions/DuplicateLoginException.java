package pl.filewicz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Użytkownik o podanym loginie już istnieje!")
public class DuplicateLoginException extends RuntimeException {
}
