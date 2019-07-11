package pl.filewicz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Nie znaleziono pokoju o podanej nazwie!")
public class RoomNotFoundException extends RuntimeException {
}
