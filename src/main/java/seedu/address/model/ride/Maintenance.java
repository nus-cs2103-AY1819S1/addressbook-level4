package seedu.address.model.ride;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Ride's days since last maintenance in the park management app.
 */
public class Maintenance {


    public static final String MESSAGE_MAINTENANCE_CONSTRAINTS =
            "Maintenance should only contain numbers with at least 1 digit long.";
    public static final String MAINTENANCE_VALIDATION_REGEX = "\\d+";
    private int value;

    /**
     * Constructs a {@code Maintenance}.
     *
     * @param daysSinceLastMaintenance Days since last maintenance.
     */
    public Maintenance(String daysSinceLastMaintenance) {
        requireNonNull(daysSinceLastMaintenance);
        checkArgument(isValidMaintenance(daysSinceLastMaintenance), MESSAGE_MAINTENANCE_CONSTRAINTS);
        value = Integer.parseInt(daysSinceLastMaintenance);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid days since last maintenance.
     */
    public static boolean isValidMaintenance(String test) {
        return test.matches(MAINTENANCE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Maintenance // instanceof handles nulls
                && value == ((Maintenance) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }

}
