package com.collin.booksocial.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * Enumeration of business error codes used within the application.
 */
public enum BusinessErrorCodes {

    /**
     * No specific code provided for the error.
     * Represents a generic case where no specific business error code applies.
     * Associated with the HTTP status NOT_IMPLEMENTED.
     */
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "Co code"),
    /**
     * Error code indicating that the current password provided is incorrect.
     * This error is raised when the user attempts to perform an action that requires
     * password validation, but the provided current password does not match
     * the stored password.
     * <ul>
     * <li>Error Code: 300</li>
     * <li>HTTP Status: BAD_REQUEST</li>
     * <li>Error Description: "Incorrect current password"</li>
     * </ul>
     */
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Incorrect current password"),
    /**
     * Error code indicating that the new password provided does not match the required criteria.
     */
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "New password does not match"),
    /**
     * Business error code representing an incorrect username or password.
     * This error occurs when a user attempts to log in with invalid credentials.
     *
     * Error Code: 302
     *
     * HTTP Status: BAD_REQUEST
     *
     * Description: "Incorrect username or password"
     */
    INCORRECT_USERNAME_OR_PASSWORD(302, BAD_REQUEST, "Incorrect username or password"),
    /**
     * Error code indicating that the user account has been disabled.
     * It is represented by the status code 303 and an HTTP status of FORBIDDEN.
     */
    ACCOUNT_DISABLED(303, FORBIDDEN, "User account disabled"),
    /**
     * Error code representing failed authentication attempt due to incorrect username or password.
     * The specified HTTP status is 403 Forbidden.
     */
    BAD_CREDENTIALS(304, FORBIDDEN, "Username or password is incorrect"),
    /**
     * Error code indicating that the user's account is locked.
     */
    ACCOUNT_LOCKED(302, FORBIDDEN, "User account locked")
    ;

    /**
     * Numeric code representing the specific business error.
     */
    @Getter
    private final int code;
    /**
     * A brief description of the business error code. This message typically provides
     * a human-readable explanation of the error condition.
     */
    @Getter
    private final String description;
    /**
     * The HTTP status associated with the business error code.
     */
    @Getter
    private final HttpStatus httpStatus;


    /**
     * Constructs a new BusinessErrorCodes enum value with the specified details.
     *
     * @param code the unique error code
     * @param httpStatus the HTTP status associated with the error
     * @param description a brief description of the error
     */
    BusinessErrorCodes(int code,HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
