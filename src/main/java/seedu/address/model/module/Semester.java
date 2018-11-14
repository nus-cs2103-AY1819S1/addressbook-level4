package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's semester in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidSemester(String)} (String)}
 *
 */
public class Semester {
    //@@author waytan
    public static final String MESSAGE_SEMESTER_CONSTRAINTS =
            "Semester should only consist of an integer between 1 and 4 inclusive";

    public static final String SEMESTER_VALIDATION_REGEX = "[1-4]{1}";

    public final String semesterNumber;

    /**
     * Empty constructor.
     */
    public Semester() {
        semesterNumber = "";
    }

    /**
     * Constructs a {@code Semester}.
     *
     * @param number The semester number.
     */
    public Semester(String number) {
        requireNonNull(number);
        checkArgument(isValidSemester(number), MESSAGE_SEMESTER_CONSTRAINTS);
        semesterNumber = number;
    }

    //@@author spikerheado1234
    /**
     * Makes an identical deep copy of this Semester.
     */
    public Semester makeCopy() {
        if (semesterNumber.equals("")) {
            return new Semester();
        }
        Semester newSemester = new Semester(new String(semesterNumber));
        return newSemester;
    }

    //@@author waytan
    /**
     * Returns true if a given number is a valid semester.
     */
    public static boolean isValidSemester(String number) {
        if (!number.matches(SEMESTER_VALIDATION_REGEX)) {
            return false;
        }
        Integer semester = Integer.parseInt(number);
        return 1 <= semester && semester <= 4;
    }

    @Override
    public String toString() {
        return semesterNumber;
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
