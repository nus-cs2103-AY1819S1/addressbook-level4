package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Serial Number of a Medicine.
 * Guarantees: immutable; is valid as declared in {@link #isValidSerialNumber(String)}
 */
public class SerialNumber {

    public static final String MESSAGE_SERIAL_NUMBER_CONSTRAINTS =
            "Serial Number should contain at least 5 digits.";
    public static final String SERIAL_NUMBER_VALIDATION_REGEX = "\\d{5,}";
    public final String value;

    /**
     * Constructs a {@code Serial Number}.
     *
     * @param serialNumber A valid serial number.
     */
    public SerialNumber(String serialNumber) {
        requireNonNull(serialNumber);
        checkArgument(isValidSerialNumber(serialNumber), MESSAGE_SERIAL_NUMBER_CONSTRAINTS);
        value = serialNumber;
    }

    /**
     * Returns true if a given string is a valid seial number.
     */
    public static boolean isValidSerialNumber(String test) {
        return test.matches(SERIAL_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SerialNumber // instanceof handles nulls
                && value.equals(((SerialNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
