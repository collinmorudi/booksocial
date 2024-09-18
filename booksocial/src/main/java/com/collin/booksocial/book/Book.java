package com.collin.booksocial.book;

import com.collin.booksocial.common.BaseEntity;
import com.collin.booksocial.feedback.Feedback;
import com.collin.booksocial.history.BookTransactionHistory;
import com.collin.booksocial.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Represents a book entity in the library system.
 * This entity includes details such as title, author, ISBN,
 * synopsis, book cover, and other related attributes.
 *
 * Extends {@link BaseEntity} to include common entity properties.
 *
 * Utilizes Lombok annotations for boilerplate code reduction:
 * {@code @Getter} and {@code @Setter} for accessor methods,
 * {@code @SuperBuilder} for builder pattern implementation,
 * {@code @AllArgsConstructor} and {@code @NoArgsConstructor} for constructors.
 *
 * Maps to a database entity through JPA annotations:
 * {@code @Entity} for specifying a JPA entity,
 * {@code @ManyToOne} and {@code @JoinColumn} for a many-to-one relationship with the User entity,
 * {@code @OneToMany} for one-to-many relationships with Feedback and BookTransactionHistory entities.
 *
 * The entity includes the following properties:
 * - title: The title of the book.
 * - authorName: The name of the author.
 * - isbn: The ISBN of the book.
 * - synopsis: A brief summary of the book.
 * - bookCover: URL or path to the book's cover image.
 * - archived: A flag indicating if the book is archived.
 * - shareable: A flag indicating if the book can be shared.
 * - owner: The user who owns the book.
 * - feedbacks: A list of feedback entries associated with the book.
 * - histories: A list of transaction histories related to the book.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

    /**
     * The title of the book.
     */
    private String title;
    /**
     * The name of the author of the book.
     */
    private String authorName;
    /**
     * The ISBN (International Standard Book Number) of the book.
     * It is a unique identifier used globally to identify books.
     */
    private String isbn;
    /**
     * A brief summary of the book.
     */
    private String synopsis;
    /**
     * The URL or path to the cover image of the book.
     */
    private String bookCover;
    /**
     * A flag indicating whether the book is archived.
     * When set to true, the book is considered archived and may not be available for certain operations.
     */
    private boolean archived;
    /**
     * A flag indicating if the book can be shared.
     */
    private boolean shareable;

    /**
     * Represents the user who owns the book.
     *
     * Defined as a many-to-one relationship with the User entity, where this book
     * contains a foreign key reference to the owner's user ID.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
     private User owner;


    /**
     * A list of feedback entries associated with the book.
     *
     * Uses a one-to-many relationship with the {@link Feedback} entity.
     * The relationship is mapped by the 'book' property in the Feedback entity.
     */
    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;
    /**
     * A collection of transaction histories associated with the book.
     * This list represents the record of all transactions like lending,
     * returning, and other relevant activities related to the book.
     * Mapped by the "book" field in the BookTransactionHistory entity.
     */
    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;
}