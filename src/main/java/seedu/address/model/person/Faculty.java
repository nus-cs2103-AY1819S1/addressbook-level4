package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's faculty in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFaculty(String)}
 */
public class Faculty {

    /**
     * The Faculties that are allowed to be used as a valid input.
     */
    enum Faculties {
        SOC, FOS, YLLSOM, FOD, BIZ, SDE, FOE, FOL, YSTCOM, FASS

    }

    public static final String MESSAGE_FACULTY_CONSTRAINTS =
            "Faculty name should be standardized with what NUS uses. "
                    + "Contacts with no faculty should have the field set to '-'.";

    /*
     * The first character of the faculty must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String FACULTY_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Faculty}.
     *
     * @param faculty A valid faculty.
     */
    public Faculty(String faculty) {
        requireNonNull(faculty);
        checkArgument(isValidFaculty(faculty), MESSAGE_FACULTY_CONSTRAINTS);
        value = faculty.toUpperCase();
    }


    /**
     * Returns true if a given string is a valid email.
     */

    public static boolean isValidFaculty(String test) {
        if (test.equals("-")) {
            return true;
        } else {
            return test.matches(FACULTY_VALIDATION_REGEX) && isInEnum(test, Faculties.class);
        }
    }

    /**
     * This method checks whether the Faculty value that has been passed in is valid or not by comparing to the
     * list of Faculties in the Faculties enum.
     * @param value The value to be checked.
     * @param enumClass The enum class that the value will be compared to.
     * @param <Faculties> The Faculties enum containing all valid faculties.
     * @return true if the Faculty is valid (found in the Faculties enum).
     */
    public static <Faculties extends Enum<Faculties>> boolean isInEnum(String value, Class<Faculties> enumClass) {
        for (Faculties e : enumClass.getEnumConstants()) {
            if (e.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Faculty // instanceof handles nulls
                && value.equals(((Faculty) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
