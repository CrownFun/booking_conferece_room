package pl.filewicz.exceptions;

public class CreateFormFormatException extends RuntimeException {
    public CreateFormFormatException(String message) {
        super("Arguments do not follow data restrictions specified in requirements " + message);
    }

    public CreateFormFormatException() {
    }
}
