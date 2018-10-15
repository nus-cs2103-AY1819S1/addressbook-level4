package seedu.address.model.person.medicalrecord;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a patient's disease.
 */
public class Disease {

    public static final String MESSAGE_DISEASE_CONSTRAINTS =
            "Disease names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String DISEASE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;

    /**
     * Constructs a {@code Disease}.
     *
     * @param disease A valid drug allergy
     */
    public Disease(String disease) {
        requireNonNull(disease);
        checkArgument(isValidDisease(disease), MESSAGE_DISEASE_CONSTRAINTS);
        value = disease;
    }

    /**
     * Returns true if a given string is a valid disease.
     */
    public static boolean isValidDisease(String test) {
        return test.matches(DISEASE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Disease // instanceof handles nulls
                && value.equals(((Disease) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
