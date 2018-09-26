package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's semester in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidSemester(Integer)} (Integer)}
 *
 * @author waytan
 */
public class Semester {
    public static final String MESSAGE_SEMESTER_CONSTRAINTS =
            "Semester should only consist of an integer between 1 and 4 inclusive";

    public final Integer semesterNumber;

    /**
     * Constructs a {@code Semester}.
     *
     * @param number The semester number.
     */
    public Semester(Integer number) {
        requireNonNull(number);
        checkArgument(isValidSemester(number), MESSAGE_SEMESTER_CONSTRAINTS);
        semesterNumber = number;
    }

    /**
     * Returns true if a given number is a valid semester.
     */
    public static boolean isValidSemester(Integer number) {
        return 1 <= number && number <= 4;
    }

    @Override
    public String toString() {
        return "Semester " + semesterNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Semester // instanceof handles nulls
                && semesterNumber.equals(((Semester) other).semesterNumber)); // state check
    }

    @Override
    public int hashCode() {
        return semesterNumber.hashCode();
    }
}
