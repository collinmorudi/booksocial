package com.collin.booksocial.handler;


import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.collin.booksocial.handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * GlobalExceptionHandler is responsible for handling exceptions globally across
 * the application. It provides specific handlers for different types of exceptions
 * and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles LockedException and provides a ResponseEntity with a detailed exception response.
     *
     * @param exp the LockedException thrown by the application.
     * @return ResponseEntity containing the details of the exception including business error code,
     *         business error description, and the exception message.
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    /**
     * Handles DisabledException and constructs a ResponseEntity with an appropriate error message
     * and business error code indicating that the account is disabled.
     *
     * @param exp the exception instance of DisabledException
     * @return a ResponseEntity containing the error response with UNAUTHORIZED status
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }


    /**
     * Handles BadCredentialsException by returning an appropriate response entity
     * with error details.
     *
     * @return a ResponseEntity containing ExceptionResponse with business error code,
     *         description, and error message, and a status of UNAUTHORIZED (401).
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                                .error(BAD_CREDENTIALS.getDescription())
                                .build()
                );
    }

    /**
     * Handles exceptions of type MessagingException, returning a standardized error response.
     *
     * @param exp the MessagingException instance that was thrown
     * @return a ResponseEntity containing the ExceptionResponse with error details and an INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    /**
     * Handles the MethodArgumentNotValidException and returns a custom response entity
     * with a set of validation error messages.
     *
     * @param exp the MethodArgumentNotValidException containing validation errors
     * @return a ResponseEntity containing an ExceptionResponse with details of the validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .vaidationErrors(errors)
                                .build()
                );
    }

    /**
     * Handles general exceptions, logs the stack trace, and returns a standardized error response.
     *
     * @param exp the exception that was thrown
     * @return a ResponseEntity containing an ExceptionResponse with a business error description
     *         and the exception's message, along with a status of INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();  // log the exception
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, please contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }
}
