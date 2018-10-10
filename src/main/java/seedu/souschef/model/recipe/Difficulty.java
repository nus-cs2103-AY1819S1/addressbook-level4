package seedu.souschef.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's cook time in the application content.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty {


    public static final String MESSAGE_DIFFICULTY_CONSTRAINTS =
            "Difficulty should only be a integer between 1 to 5 (inclusive)";
    public static final String DIFFICULTY_VALIDATION_REGEX = "^[1-5]?";
    public final int value;

    /**
     * Constructs a {@code CookTime}.
     *
     * @param diff A valid cook time.
     */
    public Difficulty(int diff) {
        checkArgument(isValidDifficulty(((Integer) diff).toString()), MESSAGE_DIFFICULTY_CONSTRAINTS);
        value = diff;
    }

    public Difficulty(String diff) {
        requireNonNull(diff);
        checkArgument(isValidDifficulty(diff), MESSAGE_DIFFICULTY_CONSTRAINTS);
        value = Integer.parseInt(diff);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDifficulty(String diff) {
        return diff.matches(DIFFICULTY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Difficulty // instanceof handles nulls
                && value == ((Difficulty) other).value); // state check
    }

    @Override
    public int hashCode() {
        return ((Integer) value).hashCode();
    }

}
