package com.collin.booksocial.feedback;

import com.collin.booksocial.book.Book;
import com.collin.booksocial.common.BaseEntity;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Feedback is an entity that represents user feedback for books.
 * It includes a numerical note, a comment, and a reference to the book it is related to.
 *
 * Attributes:
 * - note: A Double that represents the rating or score given in the feedback.
 * - comment: A String containing additional text provided by the user.
 * - book: A reference to the Book entity that this feedback is associated with.
 *
 * This class is built using Lombok annotations for boilerplate code reduction and extends BaseEntity.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback extends BaseEntity {

    /**
     * The rating or score given by the user as part of their feedback.
     * This value is represented as a Double.
     */
    private Double note;
    /**
     * A String containing additional text provided by the user as part of their feedback.
     */
    private String comment;

    /**
     * The Book associated with this feedback.
     *
     * This variable represents a many-to-one relationship with the Book entity.
     * The 'book_id' column in the feedback table is used to join with the Book entity.
     */
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
