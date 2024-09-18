package com.collin.booksocial.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BookResponse represents the response object containing details of a book.
 * It includes attributes such as id, title, author name, ISBN, synopsis,
 * owner, cover image, rate, archived status, and shareable status.
 * This class provides getter and setter methods for accessing and modifying the attributes.
 * Additionally, it includes annotations for constructing instances with various configurations.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {

    private Integer id;
    /**
     * Represents the title of the book.
     */
    private String title;
    /**
     * The name of the author of the book.
     */
    private String authorName;
    /**
     * The ISBN (International Standard Book Number) of the book.
     * This is a unique identifier assigned to each edition of a book,
     * allowing for more efficient marketing and ordering of books.
     */
    private String isbn;
    /**
     * A brief summary or general overview of the book's content.
     * This description provides potential readers with an insight or
     * snapshot of what the book is about without revealing critical
     * plot points or conclusions.
     */
    private String synopsis;
    /**
     * Represents the owner of the book.
     */
    private String owner;
    /**
     * The cover image of the book.
     * This is stored as a byte array representing the image data.
     */
    private byte[] cover;
    /**
     * Represents the average rating of the book.
     * This rate is a numerical value that reflects the overall assessment or feedback from readers.
     */
    private double rate;
    /**
     * Indicates whether the book is archived.
     * This boolean flag is used to mark a book as archived or not.
     */
    private boolean archived;
    /**
     * Indicates whether the book can be shared with others or not.
     */
    private boolean shareable;

}