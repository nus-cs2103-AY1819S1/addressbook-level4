package seedu.address.model.doctor;

//@@author jjlee050

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Doctor's id in the ClinicIO.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "ID should only contain numbers, and it should more than 1";

    public static final String ID_VALIDATION_REGEX = "[\\p{Digit}]*";
    public final int id;
    
    /**
     * Constructs a {@code Id}.
     *
     * @param doctorId A valid doctor id.
     */
    public Id(int doctorId) {
        checkArgument(isValidId(String.valueOf(doctorId)), MESSAGE_NAME_CONSTRAINTS);
        id = doctorId;
    }

    /**
     * Returns true if a given string is a valid doctor id.
     */
    public static boolean isValidId(String test) {
        return test.matches(ID_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Id // instanceof handles nulls
                && id == (((Id) other).id)); // state check
    }

    @Override
    public int hashCode() {
        return id;
    }
}
