package seedu.modsuni.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's code in the application
 */
public class Code implements Comparable<Code> {

    public static final String MESSAGE_CODE_CONSTRAINTS =
            "Code should only contain alphanumeric characters and forward slash";

    /*
     * The first character of the modsuni must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "^[A-Za-z0-9/]+$";

    public final String code;

    /**
     * Constructs a {@code Code}.
     *
     * @param code A valid code.
     */
    public Code(String code) {
        requireNonNull(code);
        checkArgument(isValidCode(code), MESSAGE_CODE_CONSTRAINTS);
        this.code = code.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCode(String test) {
        requireNonNull(test);
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Code // instanceof handles nulls
                && code.equals(((Code) other).code)); // state check
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public int compareTo(Code compareCode) {
        return this.code.compareTo(compareCode.code);
    }
}
