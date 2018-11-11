package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Project's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ProjectName {

    public static final String MESSAGE_PROJECT_NAME_CONSTRAINTS = "Assignment names should not be blank";

    /*
     * The first character of the project name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullProjectName;

    /**
     * Constructs a {@code Project Name}.
     *
     * @param name A valid name.
     */
    public ProjectName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_PROJECT_NAME_CONSTRAINTS);
        fullProjectName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullProjectName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectName // instanceof handles nulls
                && fullProjectName.equals(((ProjectName) other).fullProjectName)); // state check
    }

    @Override
    public int hashCode() {
        return fullProjectName.hashCode();
    }

}
