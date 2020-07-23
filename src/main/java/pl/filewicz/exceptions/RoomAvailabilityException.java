package pl.filewicz.exceptions;

public class RoomAvailabilityException extends RuntimeException {
    public RoomAvailabilityException() {
    }

    public RoomAvailabilityException(String message) {
        super("Can not make a reservation within a chosen period! " +message);
    }
}
