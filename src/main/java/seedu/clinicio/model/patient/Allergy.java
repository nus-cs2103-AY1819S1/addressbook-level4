package seedu.clinicio.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Allergy in the ClinicIO.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAllergy(String)}
 */
public class Allergy {
    public static final String MESSAGE_ALLERGY_CONSTRAINTS = "Allergy should be alphanumeric";
    public static final String ALLERGY_VALIDATION_REGEX = "[\\p{Alnum} ]*";

    public final String allergy;

    /**
     * Constructs a {@code Allergy}.
     *
     * @param allergy A valid allergy.
     */
    public Allergy(String allergy) {
        requireNonNull(allergy);
        checkArgument(isValidAllergy(allergy), MESSAGE_ALLERGY_CONSTRAINTS);
        this.allergy = allergy;
    }

    /**
     * Returns true if a given string is a valid allergies.
     */
    public static boolean isValidAllergy(String test) {
        return test.matches(ALLERGY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Allergy // instanceof handles nulls
                && allergy.equals(((Allergy) other).allergy)); // state check
    }

    @Override
    public int hashCode() {
        return allergy.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + allergy + ']';
    }

}
