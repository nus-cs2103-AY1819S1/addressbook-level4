package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's priority value in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriorityValue(String)}
 */
public class PriorityValue {

    //private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_PRIORITY_VALUE_CONSTRAINTS = "Priority value should be a positive integer. ";
    // alphanumeric and special characters
    //private static final String LOCAL_PART_REGEX = "^[\\w" + SPECIAL_CHARACTERS + "]+";
    //private static final String DOMAIN_FIRST_CHARACTER_REGEX = "[^\\W_]"; // alphanumeric characters except underscore
    //private static final String DOMAIN_MIDDLE_REGEX = "[a-zA-Z0-9.-]*"; // alphanumeric, period and hyphen
    //private static final String DOMAIN_LAST_CHARACTER_REGEX = "[^\\W_]$";
    public static final String PRIORITY_VALUE_VALIDATION_REGEX = "^[1-9][0-9]*$";

    public final String value;

    /**
     * Constructs an {@code PriorityValue}.
     *
     * @param priorityValue A valid priorityValue value.
     */
    public PriorityValue(String priorityValue) {
        requireNonNull(priorityValue);
        checkArgument(isValidPriorityValue(priorityValue), MESSAGE_PRIORITY_VALUE_CONSTRAINTS);
        value = priorityValue;
    }

    /**
     * Returns if a given string is a valid priority value.
     */
    public static boolean isValidPriorityValue(String test) {
        return test.matches(PRIORITY_VALUE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PriorityValue // instanceof handles nulls
                && value.equals(((PriorityValue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
