package com.collin.booksocial.feedback;

import com.collin.booksocial.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * FeedbackController class manages the REST endpoints for feedback related operations.
 * It provides endpoints to save feedback and retrieve feedbacks for a specific book.
 */
@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedbackController {

    /**
     * Service handling the business logic for saving and retrieving feedback data.
     * This is used by the FeedbackController to delegate operations related to feedback.
     */
    private final FeedbackService service;

    /**
     * Saves the feedback provided by the user.
     *
     * @param request The feedback request containing the details of the feedback.
     * @param connectedUser The authenticated user submitting the feedback.
     * @return A ResponseEntity containing the ID of the saved feedback.
     */
    @PostMapping
    public ResponseEntity<Integer> saveFeedback(
            @Valid @RequestBody FeedbackRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    /**
     * Retrieves a paginated list of feedbacks for a specific book.
     *
     * @param bookId       the ID of the book for which feedbacks are to be retrieved
     * @param page         the page number to retrieve, default value is 0
     * @param size         the size of the page to retrieve, default value is 10
     * @param connectedUser the authenticated user making the request
     * @return ResponseEntity containing a paginated response of feedback information
     */
    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> findAllFeedbacksByBook(
            @PathVariable("book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllFeedbacksByBook(bookId, page, size, connectedUser));
    }
}
