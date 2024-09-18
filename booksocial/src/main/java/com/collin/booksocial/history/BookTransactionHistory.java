package com.collin.booksocial.history;

import com.collin.booksocial.book.Book;
import com.collin.booksocial.common.BaseEntity;
import com.collin.booksocial.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents the history of transactions for a book, recording details such as the user involved,
 * the book in question, and the status of the book's return.
 *
 * Utilizes Lombok annotations for boilerplate code reduction and JPA annotations for ORM mapping.
 *
 * Fields:
 * - user: The user involved in the book transaction.
 * - book: The book involved in the transaction.
 * - returned: A boolean indicating if the book has been returned.
 * - returnApproved: A boolean indicating if the return has been approved.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookTransactionHistory extends BaseEntity {

    /**
     * The user involved in the book transaction.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * Represents the book involved in the transaction.
     * This field is mapped to the 'book_id' column in the corresponding database table.
     *
     * Managed as a many-to-one relationship, allowing multiple transaction records to reference the same book.
     */
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    /**
     * Indicates whether the book involved in the transaction has been returned.
     */
    private boolean returned;
    /**
     * Indicates whether the return of the book has been approved.
     */
    private boolean returnApproved;
}
