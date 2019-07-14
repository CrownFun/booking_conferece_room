package pl.filewicz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LOCKED, reason = "The command can not be executed. You need an administrator's permission to perform this action. Enter the correct administrator password")
public class AdministratorSecurityException extends RuntimeException {
}
