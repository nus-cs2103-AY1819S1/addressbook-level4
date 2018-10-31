package seedu.parking.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.parking.commons.util.AppUtil.checkArgument;

/**
 * Represents the total lots in a car park.
 * Guarantees: immutable; is valid as declared in {@link #isValidTotalLots(String)}
 */
public class TotalLots {

    public static final String MESSAGE_TOTAL_LOTS_CONSTRAINTS =
            "Total lots should only contain numbers";

    public static final String TOTAL_LOTS_VALIDATION_REGEX = "\\d+";

    private final String value;

    /**
     * Constructs an {@code TotalLots}.
     *
     * @param totalLots A valid total lots number.
     */
    public TotalLots(String totalLots) {
        requireNonNull(totalLots);
        checkArgument(isValidTotalLots(totalLots), MESSAGE_TOTAL_LOTS_CONSTRAINTS);
        this.value = totalLots;
    }

    /**
     * Returns true if a given string is a valid total lots number.
     */
    public static boolean isValidTotalLots(String test) {
        return test.matches(TOTAL_LOTS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TotalLots // instanceof handles nulls
                && value.equals(((TotalLots) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
