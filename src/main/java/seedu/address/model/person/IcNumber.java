package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Object class representing an IC Number of a patient.
 */
public class IcNumber {
    public static final String MESSAGE_ICNUMBER_CONSTRAINTS =
            "IC Numbers should contain a letter followed by 7 digits, followed by another letter";
    public static final String ICNUMBER_VALIDATION_REGEX = "\\D{1}\\d{7}\\D{1}";
    public final String value;

    /**
     * Constructs a {@code IcNumber}.
     *
     * @param icNumber A valid IC number.
     */
    public IcNumber(String icNumber) {
        requireNonNull(icNumber);
        checkArgument(isValidIcNumber(icNumber), MESSAGE_ICNUMBER_CONSTRAINTS);
        value = icNumber;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidIcNumber(String test) {
        return test.matches(ICNUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IcNumber // instanceof handles nulls
                && value.equals(((IcNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
