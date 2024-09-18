package com.collin.booksocial.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for performing database operations on the Book entity.
 * It extends JpaRepository to provide standard CRUD operations and
 * JpaSpecificationExecutor for supporting queries with specifications.
 */
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    /**
     * Retrieves a page of books that are not archived, shareable, and not created by the specified user.
     *
     * @param pageable the pagination information.
     * @param userId the ID of the user who should be excluded from the results.
     * @return a page of books matching the criteria.
     */
    @Query("""
            SELECT book
            FROM Book book
            WHERE book.archived = false
            AND book.shareable = true
            AND book.createdBy != :userId
            """)
    Page<Book> findAllDisplayableBooks(Pageable pageable, Integer userId);
}