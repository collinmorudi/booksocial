package com.collin.booksocial.feedback;

import com.collin.booksocial.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service class responsible for mapping between Feedback and its request/response representations.
 */
@Service
public class FeedbackMapper {
    /**
     * Converts a FeedbackRequest object into a Feedback object.
     *
     * @param request the FeedbackRequest object containing note, comment, and bookId.
     * @return the constructed Feedback object with the provided details.
     */
    public Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .shareable(false) // Not required and has no impact :: just to satisfy lombok
                        .archived(false) // Not required and has no impact :: just to satisfy lombok
                        .build()
                )
                .build();
    }

    /**
     * Converts a Feedback object into a FeedbackResponse.
     *
     * @param feedback the feedback object to be converted
     * @param id the id of the user to check if they created the feedback
     * @return a FeedbackResponse object with the note, comment, and a flag indicating if it is the user's own feedback
     */
    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), id))
                .build();
    }
}