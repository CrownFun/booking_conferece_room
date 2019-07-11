package pl.filewicz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Pokój o podanej nazwie już istnieje!")
public class DuplicateRoomException extends RuntimeException {
}
