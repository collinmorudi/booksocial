package com.collin.booksocial.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * FeedbackRequest represents a request to provide feedback consisting of a rating (note),
 * comment, and the associated book identifier (bookId).
 *
 * @param note    The rating provided as feedback. It must be a positive number between 0 and 5.
 *                Validation messages:
 *                - "200" for any positive number.
 *                - "201" if the value is below the minimum value.
 *                - "202" if the value exceeds the maximum value.
 *
 * @param comment The comment provided as feedback. It cannot be null, empty, or blank.
 *                Validation message:
 *                - "203" for any violation of null, empty, or blank constraints.
 *
 * @param bookId  The identifier of the book the feedback is related to. It cannot be null.
 *                Validation message:
 *                - "204" if the bookId is null.
 */
public record FeedbackRequest(
        @Positive(message = "200")
        @Min(value = 0, message = "201")
        @Max(value = 5, message = "202")
        Double note,
        @NotNull(message = "203")
        @NotEmpty(message = "203")
        @NotBlank(message = "203")
        String comment,
        @NotNull(message = "204")
        Integer bookId
) {
}