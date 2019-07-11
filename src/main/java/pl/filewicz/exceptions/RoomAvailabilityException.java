package pl.filewicz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Nie można dokonać rezerwacji w wybranym terminie!")
public class RoomAvailabilityException extends RuntimeException {
}
