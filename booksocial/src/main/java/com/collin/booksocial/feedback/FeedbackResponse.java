package com.collin.booksocial.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FeedbackResponse class represents a user's feedback, including a numeric rating, a written comment,
 * and a flag indicating if the feedback was provided by the user themselves.
 *
 * It uses Lombok annotations for boilerplate code reduction such as getters, setters, constructors, and the builder pattern.
 *
 * - note: A numeric rating (as a Double) of the feedback.
 * - comment: A textual comment associated with the feedback message.
 * - ownFeedback: A boolean flag indicating if the feedback was provided by the user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackResponse {

    private Double note;
    private String comment;
    private boolean ownFeedback;
}