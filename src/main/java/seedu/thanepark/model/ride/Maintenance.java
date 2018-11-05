package seedu.thanepark.model.ride;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.commons.util.AppUtil.checkArgument;

/**
 * Represents a Ride's days since last maintenance in the park management app.
 */
public class Maintenance implements NumericAttribute {

    public static final String MESSAGE_MAINTENANCE_CONSTRAINTS =
            "Maintenance should only contain positive integers with at least 1 digit long"
                   + " and should be less than 1 billion";
    public static final String MAINTENANCE_VALIDATION_REGEX = "\\d+";
    private int value;

    /**
     * Constructs a {@code Maintenance}.
     *
     * @param daysSinceMaintenanceString Days since last maintenance.
     */
    public Maintenance(String daysSinceMaintenanceString) {
        requireNonNull(daysSinceMaintenanceString);
        checkArgument(isValidMaintenance(daysSinceMaintenanceString), MESSAGE_MAINTENANCE_CONSTRAINTS);
        value = Integer.parseInt(daysSinceMaintenanceString);
    }

    /**
     * Constructs a {@code Maintenance}.
     * @param daysSinceMaintenance Days since last maintenance.
     */
    public Maintenance(int daysSinceMaintenance) {
        value = daysSinceMaintenance;
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
        return test.matches(MAINTENANCE_VALIDATION_REGEX) && test.length() < 10;
    }

    @Override
    public String toString() {
        return String.valueOf(value) + " days";
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
