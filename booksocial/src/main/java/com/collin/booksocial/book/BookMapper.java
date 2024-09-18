package com.collin.booksocial.book;

import com.collin.booksocial.file.FileUtils;
import com.collin.booksocial.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

/**
 * A service class responsible for mapping between different representations of a book.
 */
@Service
public class BookMapper {
    /**
     * Converts a BookRequest object into a Book entity.
     *
     * @param request The BookRequest object containing the book details.
     * @return A Book entity containing the information provided in the request.
     */
    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .isbn(request.isbn())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    /**
     * Converts a given book entity to a BookResponse object.
     *
     * @param book the book entity to be converted
     * @return a BookResponse representation of the given book entity
     */
    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                 .owner(book.getOwner().fullName())
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }


    /**
     * Converts a BookTransactionHistory object to a BorrowedBookResponse object.
     *
     * @param history The BookTransactionHistory object containing the transaction details of the book.
     * @return A BorrowedBookResponse object representing the details of the borrowed book.
     */
    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        return BorrowedBookResponse.builder()
                .id(history.getBook().getId())
                .title(history.getBook().getTitle())
                .authorName(history.getBook().getAuthorName())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().getRate())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }
}