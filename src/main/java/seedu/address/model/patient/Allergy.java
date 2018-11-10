package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Allergy in Patient's Medical History
 */

public class Allergy {

    public static final String MESSAGE_ALLERGY_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * the first character of the allergy must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ALLERGY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String allergy;

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

    public String getAllergy() {
        return allergy;
    }

    /**
     * Returns true if a given string is a Allergy
     */
    public static boolean isValidAllergy(String test) {
        return test.matches(ALLERGY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return allergy;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Allergy // instanceof handles nulls
                && allergy.toLowerCase().equals(((Allergy) other).getAllergy().toLowerCase()));
    }

    @Override
    public int hashCode() {
        return allergy.hashCode();
    }

}
