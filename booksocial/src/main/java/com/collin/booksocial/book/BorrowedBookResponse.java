package com.collin.booksocial.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the response details for a borrowed book.
 *
 * This class contains information about a book that has been borrowed,
 * including its identification details, title, author's name, ISBN,
 * borrowing rate, and the status of its return.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowedBookResponse {

    /**
     * The unique identifier for the borrowed book response.
     */
    private Integer id;
    /**
     * The title of the borrowed book.
     */
    private String title;
    /**
     * The name of the author of the borrowed book.
     */
    private String authorName;
    /**
     * The International Standard Book Number (ISBN) of the borrowed book.
     *
     * This is a unique identifier for books, allowing for efficient
     * management and tracking of borrowed books in the system.
     */
    private String isbn;
    /**
     * The borrowing rate of the book.
     *
     * This variable represents the rate at which the book has been borrowed,
     * potentially indicating the frequency or cost of borrowing.
     */
    private double rate;
    /**
     * Indicates whether the borrowed book has been returned.
     *
     * This variable holds the status of the borrowed book's return.
     * If true, the book has been returned; if false, it has not been returned.
     */
    private boolean returned;
    /**
     * Indicates whether the return of the borrowed book has been approved.
     */
    private boolean returnApproved;
}