package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Medicine Name in prescription
 */
public class MedicineName {

    public static final String MESSAGE_MEDICINE_NAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * the first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MEDICINE_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String fullMedicineName;

    /**
     * Constructs a {@code MedicineName}.
     *
     * @param medicineName A valid name.
     */
    public MedicineName(String medicineName) {
        requireNonNull(medicineName);
        checkArgument(isValidMedicineName(medicineName), MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        fullMedicineName = medicineName;
    }

    public MedicineName() {
        fullMedicineName = "";
    }

    public String getFullMedicineName() {
        return fullMedicineName;
    }

    /**
     * Returns true if a given string is a valid MedicineName.
     */
    public static boolean isValidMedicineName(String test) {
        return test.matches(MEDICINE_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullMedicineName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineName // instanceof handles nulls
                && fullMedicineName.toLowerCase().equals(((MedicineName) other).fullMedicineName.toLowerCase()));
    }

    @Override
    public int hashCode() {
        return fullMedicineName.hashCode();
    }

}
