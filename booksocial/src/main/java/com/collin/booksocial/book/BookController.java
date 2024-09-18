package com.collin.booksocial.book;

import com.collin.booksocial.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * The BookController class handles HTTP requests related to book operations such as saving,
 * retrieving, updating, borrowing, and returning books. It also handles the uploading of book cover pictures.
 */
@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    /**
     * Service responsible for handling all book-related operations.
     */
    private final BookService service;

    /**
     * Saves a book based on the provided request and the authenticated user.
     *
     * @param request the book request containing book details to be saved
     * @param connectedUser the authenticated user performing the save operation
     * @return a ResponseEntity containing the integer ID of the saved book
     */
    @PostMapping
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    /**
     * Finds a book by its unique identifier.
     *
     * @param bookId the unique identifier of the book to find
     * @return a ResponseEntity containing the BookResponse if found, otherwise an appropriate error response
     */
    @GetMapping("/{book-id}")
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable("book-id") Integer bookId
    ) {
        return ResponseEntity.ok(service.findById(bookId));
    }

    /**
     * Retrieves a paginated list of all books.
     *
     * @param page the page number to retrieve, optional with a default value of 0.
     * @param size the number of records per page, optional with a default value of 10.
     * @param connectedUser the authentication object representing the connected user.
     * @return ResponseEntity containing a PageResponse with the paginated list of BookResponse objects.
     */
    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBooks(page, size, connectedUser));
    }

    /**
     * Retrieves a paginated list of books owned by the authenticated user.
     *
     * @param page the page number to retrieve, defaults to 0 if not provided
     * @param size the number of records per page, defaults to 10 if not provided
     * @param connectedUser the current authenticated user
     * @return a ResponseEntity containing a PageResponse of BookResponse objects
     */
    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBooksByOwner(page, size, connectedUser));
    }

    /**
     * Retrieves a paginated list of all borrowed books for the authenticated user.
     *
     * @param page The page number to retrieve, starting from 0.
     * @param size The number of records per page.
     * @param connectedUser The authenticated user requesting the data.
     * @return A ResponseEntity containing a PageResponse with a list of BorrowedBookResponse objects.
     */
    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBorrowedBooks(page, size, connectedUser));
    }

    /**
     * Retrieves a paginated list of all returned books.
     *
     * @param page the page number to retrieve, defaults to 0 if not provided
     * @param size the size of the page to retrieve, defaults to 10 if not provided
     * @param connectedUser the authenticated user making the request
     * @return a ResponseEntity containing a PageResponse of BorrowedBookResponse for all returned books
     */
    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllReturnedBooks(page, size, connectedUser));
    }

    /**
     * Updates the shareable status of a book identified by its ID.
     *
     * @param bookId the ID of the book whose shareable status is to be updated
     * @param connectedUser the authenticated user making the request
     * @return a ResponseEntity containing the updated shareable status as an Integer
     */
    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> updateShareableStatus(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateShareableStatus(bookId, connectedUser));
    }

    /**
     * Updates the archived status of a book.
     *
     * @param bookId The ID of the book whose archived status is to be updated.
     * @param connectedUser The authentication details of the connected user.
     * @return ResponseEntity containing the integer result of the update operation.
     */
    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateArchivedStatus(bookId, connectedUser));
    }

    /**
     * Handles the borrowing of a book by a connected user.
     *
     * @param bookId the ID of the book to be borrowed
     * @param connectedUser the user who is attempting to borrow the book
     * @return ResponseEntity containing the ID of the borrowed book
     */
    @PostMapping("borrow/{book-id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
    }

    /**
     * Handles the return of a borrowed book.
     *
     * @param bookId the ID of the book to be returned
     * @param connectedUser the authentication information of the currently connected user
     * @return a ResponseEntity containing the status code of the return operation
     */
    @PatchMapping("borrow/return/{book-id}")
    public ResponseEntity<Integer> returnBorrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.returnBorrowedBook(bookId, connectedUser));
    }

    /**
     * Approves the return of a borrowed book.
     *
     * @param bookId the ID of the book being returned
     * @param connectedUser the authenticated user making the request
     * @return the HTTP response containing the status code of the approval operation
     */
    @PatchMapping("borrow/return/approve/{book-id}")
    public ResponseEntity<Integer> approveReturnBorrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.approveReturnBorrowedBook(bookId, connectedUser));
    }

    /**
     * Uploads a cover picture for the specified book.
     *
     * @param bookId the ID of the book for which the cover picture is to be uploaded
     * @param file the MultipartFile containing the image to be uploaded as the book cover
     * @param connectedUser the Authentication object representing the currently connected user
     * @return a ResponseEntity indicating the result of the upload operation
     */
    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") Integer bookId,
            @Parameter
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        service.uploadBookCoverPicture(file, connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }
}