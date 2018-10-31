package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents dosage in prescription
 */

public class Dosage {

    public static final String MESSAGE_CONSTRAINTS =
            "Dosage should only contain numbers, and it must be greater than 0";
    public static final String DOSAGE_VALIDATION_REGEX = "^[1-9][0-9]*$";
    private final String value;

    /**
     * Constructs a {@code Dosage}.
     *
     * @param dosage A valid dosage
     */
    public Dosage(String dosage) {
        requireNonNull(dosage);
        checkArgument(isValidDosage(dosage), MESSAGE_CONSTRAINTS);
        value = dosage;
    }

    public Dosage() {
        value = "";
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid dosage
     */
    public static boolean isValidDosage(String test) {
        return test.matches(DOSAGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Dosage // instanceof handles nulls
                && value.equals(((Dosage) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
