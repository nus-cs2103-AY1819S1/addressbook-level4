package seedu.address.model.doctor;

import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author jjlee050
/**
 * Represents a Doctor's id in the ClinicIO.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(int)}
 */
public class Id {

    public static final String MESSAGE_ID_CONSTRAINTS =
            "Id should only contain numbers, and it should more than 0";

    public final int id;

    /**
     * Constructs a {@code Id}.
     *
     * @param doctorId A valid doctor id.
     */
    public Id(int doctorId) {
        checkArgument(isValidId(doctorId), MESSAGE_ID_CONSTRAINTS);
        id = doctorId;
    }

    /**
     * Returns true if a given integer is a valid doctor id.
     */
    public static boolean isValidId(int test) {
        return test >= 0;
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
