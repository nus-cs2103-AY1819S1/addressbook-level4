package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's academic year in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidYear(Integer)} (Integer)}
 *
 * @author waytan
 */
public class AcademicYear {
    public static final String MESSAGE_ACADEMICYEAR_CONSTRAINTS =
            "Academic Year should be a 4 digit number,"
            + " with the first and last 2 digits representing the calendar year.";

    public final Integer yearNumber;

    /**
     * Constructs a {@code AcademicYear}.
     *
     * @param number The semester number.
     */
    public AcademicYear(Integer number) {
        requireNonNull(number);
        checkArgument(isValidYear(number), MESSAGE_ACADEMICYEAR_CONSTRAINTS);
        yearNumber = number;
    }

    /**
     * Returns true if a given Integer is a valid AcademicYear number
     */
    public static boolean isValidYear(Integer number) {
        Integer firstYear = number / 100;
        Integer secondYear = number % 100;
        return (firstYear + 1) % 100 == secondYear;
    }

    public Integer firstYear() {
        return yearNumber / 100;
    }

    public Integer secondYear() {
        return yearNumber % 100;
    }

    @Override
    public String toString() {
        return "AY" + firstYear() + "/" + secondYear();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcademicYear // instanceof handles nulls
                && yearNumber.equals(((AcademicYear) other).yearNumber)); // state check
    }

    @Override
    public int hashCode() {
        return yearNumber.hashCode();
    }
}
