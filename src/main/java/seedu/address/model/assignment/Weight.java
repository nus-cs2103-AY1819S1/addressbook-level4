package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an assignment's weight in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Weight implements Comparable<Weight> {

    public static final String MESSAGE_CONSTRAINTS =
            "Weights should be real number, and it should not be blank";
    public static final String VALIDATION_REGEX = "^(-+)?\\d+(\\.\\d+)?$";

    public final String internalString;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight string.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValid(weight), MESSAGE_CONSTRAINTS);
        internalString = weight;
    }

    /**
     * Returns true if a given string matches the class VALIDATION_REGEX.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int compareTo(Weight other) {
        return this.getValue().compareTo(other.getValue());
    }

    public Double getValue() {
        return Double.valueOf(this.internalString);
    }

    @Override
    public String toString() {
        return this.internalString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Weight // instanceof handles nulls
                && internalString.equals(((Weight) other).internalString)); // state check
    }
    @Override
    public int hashCode() {
        return internalString.hashCode();
    }

}
