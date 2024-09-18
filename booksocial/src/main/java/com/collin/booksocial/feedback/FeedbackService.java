package com.collin.booksocial.feedback;

import com.collin.booksocial.book.Book;
import com.collin.booksocial.book.BookRepository;
import com.collin.booksocial.common.PageResponse;
import com.collin.booksocial.exception.OperationNotPermittedException;
import com.collin.booksocial.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * The FeedbackService class provides operations for handling feedback related
 * to books. The service allows users to save feedback and retrieve all feedback
 * for a specific book.
 */
@Service
@RequiredArgsConstructor
public class FeedbackService {

    /**
     * Repository interface for CRUD operations on Feedback entities. This repository
     * handles database interactions related to Feedback objects, including saving,
     * updating, deleting, and finding feedback entries.
     */
    private final FeedBackRepository feedBackRepository;
    /**
     * Repository to handle the persistence and retrieval of book entities.
     * Used to interact with the database for operations related to the Book entity.
     */
    private final BookRepository bookRepository;
    /**
     * A mapper utility for converting feedback-related data transfer objects (DTOs)
     * to feedback entity objects and vice versa.
     * This instance is used to transform FeedbackRequest DTOs into Feedback entities and to map
     * Feedback entities into FeedbackResponse DTOs.
     */
    private final FeedbackMapper feedbackMapper;

    /**
     * Saves a feedback for a specific book provided in the FeedbackRequest.
     *
     * @param request the feedback request containing information about the feedback to be saved
     * @param connectedUser the authenticated user who is attempting to save feedback
     * @return the ID of the saved feedback
     * @throws EntityNotFoundException if the specified book is not found
     * @throws OperationNotPermittedException if the book is archived, not shareable, or if the user is trying to give feedback to their own book
     */
    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + request.bookId()));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("You cannot give a feedback for and archived or not shareable book");
        }
         User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot give feedback to your own book");
        }
        Feedback feedback = feedbackMapper.toFeedback(request);
        return feedBackRepository.save(feedback).getId();
    }

    /**
     * Finds all feedbacks for a specific book with pagination.
     *
     * @param bookId the ID of the book for which feedback is being retrieved.
     * @param page the number of the page to retrieve.
     * @param size the size of the page to retrieve.
     * @param connectedUser the currently authenticated user.
     * @return a paginated response containing feedback for the specified book.
     */
    @Transactional
    public PageResponse<FeedbackResponse> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback> feedbacks = feedBackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(f -> feedbackMapper.toFeedbackResponse(f, user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );

    }
}
