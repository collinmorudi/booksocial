package com.collin.booksocial.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public enum BusinessErrorCodes {

    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "Co code"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Incorrect current password"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "New password does not match"),
    INCORRECT_USERNAME_OR_PASSWORD(302, BAD_REQUEST, "Incorrect username or password"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "User account disabled"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Username or password is incorrect"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "User account locked")
    ;

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;


    BusinessErrorCodes(int code,HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
