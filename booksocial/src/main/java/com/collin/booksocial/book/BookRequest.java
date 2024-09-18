package com.collin.booksocial.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a request to create or update a book record.
 *
 * The BookRequest class holds information about a book, including the title, author name, ISBN, and synopsis.
 * Additionally, it indicates whether the book is shareable.
 * Each field is validated to ensure that it is not null, empty, or blank, with custom messages for validation errors.
 */
public record BookRequest(
        Integer id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        @NotBlank(message = "100")
        String title,
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        @NotBlank(message = "101")
        String authorName,
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        @NotBlank(message = "102")
        String isbn,
        @NotNull(message = "103")
        @NotEmpty(message = "103")
        @NotBlank(message = "103")
        String synopsis,
        boolean shareable
) {
}
