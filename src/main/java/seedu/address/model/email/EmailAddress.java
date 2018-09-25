package seedu.address.model.email;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Person's email address in Email.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmailAddress(String)}
 */
public class EmailAddress {

    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_EMAIL_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + ") .\n"
            + "2. This is followed by a '@' and then a domain name. "
            + "The domain name must:\n"
            + "    - be at least 2 characters long\n"
            + "    - start and end with alphanumeric characters\n"
            + "    - consist of alphanumeric characters, a period or a hyphen for the characters in between, if any.";
    // alphanumeric and special characters
    private static final String LOCAL_PART_REGEX = "^[\\w" + SPECIAL_CHARACTERS + "]+";
    private static final String DOMAIN_FIRST_CHARACTER_REGEX = "[^\\W_]"; // alphanumeric characters except underscore
    private static final String DOMAIN_MIDDLE_REGEX = "[a-zA-Z0-9.-]*"; // alphanumeric, period and hyphen
    private static final String DOMAIN_LAST_CHARACTER_REGEX = "[^\\W_]$";
    public static final String EMAIL_VALIDATION_REGEX = LOCAL_PART_REGEX + "@"
            + DOMAIN_FIRST_CHARACTER_REGEX + DOMAIN_MIDDLE_REGEX + DOMAIN_LAST_CHARACTER_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param emailAddress A valid email address.
     */
    public EmailAddress(String emailAddress) {
        requireNonNull(emailAddress);
        checkArgument(isValidEmailAddress(emailAddress), MESSAGE_EMAIL_CONSTRAINTS);
        value = emailAddress;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmailAddress(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailAddress // instanceof handles nulls
                && value.equals(((EmailAddress) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
