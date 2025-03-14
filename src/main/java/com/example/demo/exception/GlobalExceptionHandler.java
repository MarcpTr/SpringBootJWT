package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailFormat.class)
    public ResponseEntity<ErrorResponse> handleInvalidEmailFormat(InvalidEmailFormat ex) {
        ErrorResponse errorResponse = new ErrorResponse("INVALID_EMAIL", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse("RESOURCE_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(EmailAlreadyInUse.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUse(EmailAlreadyInUse ex) {
        ErrorResponse errorResponse = new ErrorResponse("EMAIL_ALREADY_IN_USE", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(InvalidLogin.class)
    public ResponseEntity<ErrorResponse> handleInvalidLogin(InvalidLogin ex) {
        ErrorResponse errorResponse = new ErrorResponse("INVALID_LOGIN", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(UsernameAlreadyInUse.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyInUse(UsernameAlreadyInUse ex) {
        ErrorResponse errorResponse = new ErrorResponse("USERNAME_ALREADY_IN_USE", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(InvalidPasswordFormat.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordFormat(InvalidPasswordFormat ex) {
        ErrorResponse errorResponse = new ErrorResponse("INVALID_PASSWORD", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("SERVER_ERROR", "Hubo un error en el servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
@Getter
@Setter
    class ErrorResponse {
        private String errorCode;
        private String message;

        public ErrorResponse(String errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

    }
}

