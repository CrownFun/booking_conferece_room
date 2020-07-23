package pl.filewicz.exceptions;
public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String message) {
        super("Room " + message + " not found! - Make sure provided room name is correct ");
    }

    public RoomNotFoundException() {
    }
}
