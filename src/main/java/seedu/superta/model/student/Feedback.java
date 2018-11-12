package seedu.superta.model.student;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Student's feedback in the address book.
 * Guarantees: immutable, is always valid
 */
public class Feedback {
    public final String value;

    /**
     * Constructs an {@code Feedback}.
     *
     * @param feedback A valid feedback.
     */
    public Feedback(String feedback) {
        requireNonNull(feedback);
        value = feedback;
    }

    public Feedback(Feedback feedback) {
        requireNonNull(feedback);
        value = feedback.value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Feedback // instanceof handles nulls
                && value.equals(((Feedback) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
