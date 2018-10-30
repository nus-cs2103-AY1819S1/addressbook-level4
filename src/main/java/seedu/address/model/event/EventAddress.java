package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class EventAddress {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the event address must not be a whitespace,
     * so that " " (a blank string) is considered an invalid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String eventAddress;

    /**
     * Constructs an {@code EventAddress}.
     *
     * @param eventAddress A valid address.
     */
    public EventAddress(String eventAddress) {
        requireNonNull(eventAddress);
        checkArgument(isValidAddress(eventAddress), MESSAGE_ADDRESS_CONSTRAINTS);
        this.eventAddress = eventAddress;
    }

    /**
     * Returns true if a given string is a valid event address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return eventAddress;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventAddress // instanceof handles nulls
                && eventAddress.equals(((EventAddress) other).eventAddress)); // state check
    }

    @Override
    public int hashCode() {
        return eventAddress.hashCode();
    }

}
