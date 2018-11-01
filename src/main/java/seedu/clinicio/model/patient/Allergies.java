package seedu.clinicio.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Allergies in the ClinicIO.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAllergies(String)}
 */
public class Allergies {
    public static final String MESSAGE_ALLERGIES_CONSTRAINTS = "Allergies should be alphanumeric";
    public static final String ALLERGIES_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String allergies;

    /**
     * Constructs a {@code Allergies}.
     *
     * @param allergies A valid allergies.
     */
    public Allergies(String allergies) {
        requireNonNull(allergies);
        checkArgument(isValidAllergies(allergies), MESSAGE_ALLERGIES_CONSTRAINTS);
        this.allergies = allergies;
    }

    /**
     * Returns true if a given string is a valid allergies.
     */
    public static boolean isValidAllergies(String test) {
        return test.matches(ALLERGIES_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Allergies // instanceof handles nulls
                && allergies.equals(((Allergies) other).allergies)); // state check
    }

    @Override
    public int hashCode() {
        return allergies.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + allergies + ']';
    }

}
