package seedu.address.model.person.medicalrecord;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class BloodType {

    public static final String MESSAGE_BLOODTYPE_CONSTRAINTS =
            "Blood types can only be one of the following: A(+-), B(+-), O(+-), or AB(+-)";
    public static final String BLOODTYPE_VALIDATION_REGEX = "^$|(A|B|AB|O)[+-]";
    public final String value;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodType A valid blood type.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        checkArgument(isValidBloodType(bloodType), MESSAGE_BLOODTYPE_CONSTRAINTS);
        value = bloodType;
    }

    /**
     * Returns true if a given string is a valid blood type.
     */
    public static boolean isValidBloodType(String test) {
        return test.matches(BLOODTYPE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodType // instanceof handles nulls
                && value.equals(((BloodType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
