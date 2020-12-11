package by.ermakovich.search_command.Exeptions;

import by.ermakovich.search_command.entity.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ InvalidFormsException.class, AuthException.class, RegisterException.class })
    public final ResponseEntity<CustomError> handleException(Exception error) {
        if (error instanceof InvalidFormsException) {
            return handleInvalidFormsException((InvalidFormsException)error);
        } else if (error instanceof AuthException) {
            return handleAuthException((AuthException)error);
        } else if (error instanceof RegisterException) {
            return handleRegisterException((RegisterException)error);
        }else {
            return handleExceptionInternal(null);
        }
    }

    protected ResponseEntity<CustomError> handleInvalidFormsException(InvalidFormsException error) {
        return handleExceptionInternal(new CustomError(error.getMessage()));
    }

    protected ResponseEntity<CustomError> handleAuthException(AuthException error) {
        return handleExceptionInternal(new CustomError(error.getMessage()));
    }

    protected ResponseEntity<CustomError> handleRegisterException(RegisterException error) {
        return handleExceptionInternal(new CustomError(error.getMessage()));
    }

    protected ResponseEntity<CustomError> handleExceptionInternal(CustomError body) {
        return new ResponseEntity<CustomError>(body, HttpStatus.BAD_REQUEST);
    }
}