package pl.filewicz.exceptions;

public class AdministratorSecurityException extends RuntimeException {
    public AdministratorSecurityException(String message) {
        super("The command can not be executed. You need an administrator's permission to perform this action. Enter the correct administrator password " + message);
    }

    public AdministratorSecurityException() {
    }
}
