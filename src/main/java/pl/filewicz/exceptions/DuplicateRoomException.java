package pl.filewicz.exceptions;

public class DuplicateRoomException extends RuntimeException {

    public DuplicateRoomException(String message) {
        super("Room with the given name " + message + " already exists");
    }

    public DuplicateRoomException() {
    }
}
