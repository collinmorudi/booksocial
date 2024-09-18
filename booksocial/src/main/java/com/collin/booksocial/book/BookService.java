package com.collin.booksocial.book;

import com.collin.booksocial.common.PageResponse;
import com.collin.booksocial.exception.OperationNotPermittedException;
import com.collin.booksocial.file.FileStorageService;
import com.collin.booksocial.history.BookTransactionHistory;
import com.collin.booksocial.history.BookTransactionHistoryRepository;
import com.collin.booksocial.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.collin.booksocial.book.BookSpecification.withOwnerId;


/**
 * Service class for managing book-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {

    /**
     * Repository responsible for managing Book entities.
     * Handles database operations related to books, including CRUD operations.
     */
    private final BookRepository bookRepository;
    /**
     * The BookMapper is responsible for converting between Book entities and their corresponding DTOs.
     * It is used within the BookService to facilitate data transfer and ensure consistency between
     * the service layer and other components such as controllers and repositories.
     */
    private final BookMapper bookMapper;
    /**
     * Repository to handle the CRUD operations for BookTransactionHistory entities.
     * Used within the BookService to manage the history of book transactions such as borrow and return.
     */
    private final BookTransactionHistoryRepository transactionHistoryRepository;
    /**
     * Service responsible for handling file storage operations.
     * Used within the BookService class for various file-related functionalities such as uploading book cover pictures.
     */
    private final FileStorageService fileStorageService;

    /**
     * Saves a new book record to the repository.
     *
     * @param request The request object containing book details.
     * @param connectedUser The authenticated user making the save request.
     * @return The ID of the saved book.
     */
    public Integer save(BookRequest request, Authentication connectedUser) {
         User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
         book.setOwner(user);
        return bookRepository.save(book).getId();
    }

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param bookId the unique identifier of the book to be retrieved
     * @return the BookResponse object representing the found book
     * @throws EntityNotFoundException if no book is found with the provided ID
     */
    public BookResponse findById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
    }

    /**
     * Retrieves a pageable list of all displayable books for the authenticated user.
     *
     * @param page the current page number to retrieve
     * @param size the number of items per page
     * @param connectedUser the authenticated user
     * @return a PageResponse containing a list of BookResponse objects and pagination details
     */
    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    /**
     * Retrieves a paginated list of books owned by the currently authenticated user.
     *
     * @param page the page number to retrieve.
     * @param size the number of records per page.
     * @param connectedUser the authenticated user object.
     * @return a PageResponse containing a list of BookResponse objects and pagination details.
     */
    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable);
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    /**
     * Retrieves a paginated list of all borrowed books for the authenticated user.
     *
     * @param page the page number to retrieve
     * @param size the size of the page to retrieve
     * @param connectedUser the Authentication object representing the currently authenticated user
     * @return a PageResponse containing a list of BorrowedBookResponse objects and pagination information
     */
    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = transactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponse = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    /**
     * Updates the shareable status of a book. This method will toggle the current shareable status of the
     * book identified by the given bookId. The operation is restricted to the owner of the book.
     *
     * @param bookId The ID of the book whose shareable status needs to be updated.
     * @param connectedUser The authenticated user making the request. The user must be the owner of the book.
     * @return The ID of the book whose shareable status was updated.
     * @throws EntityNotFoundException If no book is found with the given ID.
     * @throws OperationNotPermittedException If the authenticated user is not the owner of the book.
     */
    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot update others books shareable status");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    /**
     * Updates the archived status of a book identified by its ID.
     * The method toggles the archived status of the book if the authenticated user is the owner.
     *
     * @param bookId the ID of the book to update
     * @param connectedUser the authenticated user attempting to update the archived status
     * @return the ID of the updated book
     * @throws EntityNotFoundException if no book is found with the specified ID
     * @throws OperationNotPermittedException if the authenticated user is not the owner of the book
     */
    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
         User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot update others books archived status");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    /**
     * Borrows a book specified by its ID if it is available and conditions are met.
     *
     * @param bookId the ID of the book to be borrowed.
     * @param connectedUser the authenticated user attempting to borrow the book.
     * @return the ID of the transaction history entry created for this borrowing action.
     * @throws EntityNotFoundException if no book is found with the specified ID.
     * @throws OperationNotPermittedException if the book is archived, not shareable,
     *         already borrowed, or owned by the user attempting to borrow it.
     */
    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book cannot be borrowed since it is archived or not shareable");
        }
         User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot borrow your own book");
        }
        final boolean isAlreadyBorrowedByUser = transactionHistoryRepository.isAlreadyBorrowedByUser(bookId, user.getId());
        if (isAlreadyBorrowedByUser) {
            throw new OperationNotPermittedException("You already borrowed this book and it is still not returned or the return is not approved by the owner");
        }

        final boolean isAlreadyBorrowedByOtherUser = transactionHistoryRepository.isAlreadyBorrowed(bookId);
        if (isAlreadyBorrowedByOtherUser) {
            throw new OperationNotPermittedException("The requested book is already borrowed");
        }

        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return transactionHistoryRepository.save(bookTransactionHistory).getId();

    }

    /**
     * Handles the return process of a borrowed book by a user.
     *
     * @param bookId          the ID of the book to be returned
     * @param connectedUser   the authentication object of the user trying to return the book
     * @return the ID of the book transaction history record updated
     * @throws EntityNotFoundException      if the book with the specified ID is not found
     * @throws OperationNotPermittedException if the book is archived or not shareable, if the book is owned by the user,
     *                                        or if the user did not borrow the book
     */
    public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book is archived or not shareable");
        }
         User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot borrow or return your own book");
        }

        BookTransactionHistory bookTransactionHistory = transactionHistoryRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("You did not borrow this book"));

        bookTransactionHistory.setReturned(true);
        return transactionHistoryRepository.save(bookTransactionHistory).getId();
    }
/**
 * Approves the return of a borrowed book given its ID.
 *
 * @param bookId the ID of the book to approve return for
 * @param connectedUser the authenticated user initiating the request
 * @return the ID of the transaction history record after saving the approval status
 * @throws EntityNotFoundException if no book with the specified ID is found
 * @throws OperationNotPermittedException if the book is archived, not shareable, or the user does not own the book
 */
//
    public Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book is archived or not shareable");
        }
         User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot approve the return of a book you do not own");
        }

        BookTransactionHistory bookTransactionHistory = transactionHistoryRepository.findByBookIdAndOwnerId(bookId, connectedUser.getName())
                .orElseThrow(() -> new OperationNotPermittedException("The book is not returned yet. You cannot approve its return"));

        bookTransactionHistory.setReturnApproved(true);
        return transactionHistoryRepository.save(bookTransactionHistory).getId();
    }
/**
 * Uploads a new cover picture for the specified book.
 *
 * @param file the MultipartFile containing the book cover image
 * @param connectedUser the authenticated user making the request
 * @param bookId the ID of the book for which the cover image is being uploaded
 */
//
    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));
        User user = ((User) connectedUser.getPrincipal());
        var bookCover = fileStorageService.saveFile(file, user.getId());
        book.setBookCover(bookCover);
        bookRepository.save(book);
    }

    /**
     * Retrieves a paginated list of all books that have been returned by the current user.
     *
     * @param page The page number to retrieve, starting from zero.
     * @param size The number of records per page.
     * @param connectedUser The authentication object representing the currently connected user.
     * @return A paginated response containing the list of returned books and pagination information.
     */
    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
         User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = transactionHistoryRepository.findAllReturnedBooks(pageable, user.getId());
        List<BorrowedBookResponse> booksResponse = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }
}