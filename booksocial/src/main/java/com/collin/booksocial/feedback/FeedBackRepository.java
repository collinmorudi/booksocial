package com.collin.booksocial.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * FeedBackRepository is a data access interface that extends JpaRepository to provide
 * CRUD operations and custom queries for Feedback entities.
 * It includes a method to retrieve Feedback entries associated with a specific book.
 */
public interface FeedBackRepository extends JpaRepository<Feedback, Integer> {
    /**
     * Retrieves a page of Feedback entries associated with a specific book.
     *
     * @param bookId the ID of the book whose feedback entries are to be retrieved
     * @param pageable the pagination information
     * @return a Page containing Feedback entries for the specified book
     */
    @Query("""
            SELECT feedback
            FROM Feedback  feedback
            WHERE feedback.book.id = :bookId
""")
    Page<Feedback> findAllByBookId(Integer bookId, Pageable pageable);
}