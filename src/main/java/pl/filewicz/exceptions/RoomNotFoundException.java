package pl.filewicz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Room not found! - Make sure provided room name is correct")
public class RoomNotFoundException extends RuntimeException {
}
