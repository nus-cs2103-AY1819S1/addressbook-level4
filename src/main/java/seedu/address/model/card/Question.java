package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represent an question to a card.
 */
public class Question {
    public static final String MESSAGE_QUESTION_CONSTRAINTS = "Question must not be an empty string.";
    public final String value;

    /**
     * Constructs a {@code question}.
     *
     * @param question A question.
     */
    public Question(String question) {
        requireNonNull(question);
        checkArgument(isValidQuestion(question), MESSAGE_QUESTION_CONSTRAINTS);
        this.value = question;
    }

    /**
     * Returns if a given string is a valid question.
     */
    public static boolean isValidQuestion(String test) {
        return !test.trim().equals("");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Question
            && value.equals(((Question) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
