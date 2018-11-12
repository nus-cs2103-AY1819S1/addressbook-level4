package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's academic capabilities in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */

public class Grades {

    public static final String MESSAGE_GRADE_INPUT_CONSTRAINTS =
            "Grades input should contain "
                    + "an exam name and a positive integer range from 0 to 100 which are separated by space";

    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grade should only contain a positive integer range from 0 to 100, and it should not be blank";

    public static final String GRADE_VALIDATION_REGEX = "^(?:\\d?\\d|100)$";

    public static final String GRADE_INPUT_VALIDATION_REGEX = "^(\\S+)(\\s+)(?:\\d?\\d|100)$";

    public final String value;

    /**
     * Constructs an {@code Grade}.
     *
     * @param grade A valid address.
     */
    public Grades(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        value = grade;
    }

    /**
     * Returns true if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(GRADE_VALIDATION_REGEX);
    }

    public static boolean isValidGradeInput(String test) {
        return test.matches(GRADE_INPUT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grades // instanceof handles nulls
                && value.equals(((Grades) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(value).hashCode();
    }
}
