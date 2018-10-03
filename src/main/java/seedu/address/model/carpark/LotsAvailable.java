package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the lots available in a carpark in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLotsAvail(String)}
 */
public class LotsAvailable {

    public static final String MESSAGE_LOTS_AVAIL_CONSTRAINTS =
            "Lots available should only contain numbers";

    public static final String LOTS_AVAIL_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs an {@code TotalLots}.
     *
     * @param lotsAvail A valid lots available number.
     */
    public LotsAvailable(String lotsAvail) {
        requireNonNull(lotsAvail);
        checkArgument(isValidLotsAvail(lotsAvail), MESSAGE_LOTS_AVAIL_CONSTRAINTS);
        this.value = lotsAvail;
    }

    /**
     * Returns true if a given string is a valid lots available number.
     */
    public static boolean isValidLotsAvail(String test) {
        return test.matches(LOTS_AVAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LotsAvailable // instanceof handles nulls
                && value.equals(((LotsAvailable) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
