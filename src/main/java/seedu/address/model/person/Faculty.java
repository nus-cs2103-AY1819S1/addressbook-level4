package seedu.address.model.person;

// import static java.util.Objects.requireNonNull;
// import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's faculty in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFaculty(String)}
 */
public class Faculty {

    public static final String MESSAGE_FACULTY_CONSTRAINTS =
            "Faculty name should be standardized with what NUS uses."
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
        // requireNonNull(faculty);
        // checkArgument(isValidFaculty(faculty), MESSAGE_FACULTY_CONSTRAINTS);
        value = faculty;
    }


    /**
     * Returns true if a given string is a valid email.
     */

    public static boolean isValidFaculty(String test) {
        return test.matches(FACULTY_VALIDATION_REGEX);
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
