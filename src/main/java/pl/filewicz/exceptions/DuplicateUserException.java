package pl.filewicz.exceptions;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message) {
        super("User with the given login " + message + " already exists!");
    }

    public DuplicateUserException() {
    }
}
