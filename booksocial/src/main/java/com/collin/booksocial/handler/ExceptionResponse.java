package com.collin.booksocial.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

/**
 * A class representing the structure of an exception response.
 * This class contains details about business errors, validation errors,
 * and other general errors that might occur.
 *
 * The fields in this class are:
 * - businessErrorCode: An integer code representing the business error.
 * - businessErrorDescription: A description of the business error.
 * - error: A general error message.
 * - vaidationErrors: A set of validation error messages.
 * - errors: A map containing additional error details where the key is the
 *   field name and the value is the corresponding error message.
 *
 * This class includes various annotations:
 * - @Getter and @Setter: To generate getter and setter methods.
 * - @Builder: To implement the builder pattern for this class.
 * - @AllArgsConstructor: To generate a constructor with all the fields.
 * - @NoArgsConstructor: To generate a no-argument constructor.
 * - @JsonInclude(JsonInclude.Include.NON_EMPTY): To include only non-empty
 *   fields in the JSON serialization process.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    private Integer businessErrorCode;
    private String businessErrorDescription;
    private String error;
    private Set<String> vaidationErrors;
    private Map<String, String> errors;

}
