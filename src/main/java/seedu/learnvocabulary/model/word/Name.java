package seedu.learnvocabulary.model.word;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.commons.util.AppUtil.checkArgument;

/**
 * Represents a Word's name in LearnVocabulary.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of LearnVocabulary must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */

    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        name = Dictionary.convertWord(name);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
