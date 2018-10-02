package seedu.address.model.word;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMeaning(String)}
 */
public class Meaning {

    public static final String MESSAGE_MEANING_CONSTRAINTS =
            "Meaning of the word should not be left blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Meaning}.
     *
     * @param meaning A valid address.
     */
    public Meaning(String meaning) {
        requireNonNull(meaning);
        checkArgument(isValidMeaning(meaning), MESSAGE_MEANING_CONSTRAINTS);
        value = meaning;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidMeaning(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meaning // instanceof handles nulls
                && value.equals(((Meaning) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
