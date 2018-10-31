package seedu.thanepark.model.ride;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a Ride's days since last maintenance in the park management app.
 */
public class Maintenance implements NumericAttribute {

    public static final String MESSAGE_MAINTENANCE_CONSTRAINTS =
            "Maintenance should only contain numbers with at least 1 digit long.";
    public static final String MAINTENANCE_VALIDATION_REGEX = "\\d+";
    private LocalDate lastMaintenanceDate;

    /**
     * Constructs a {@code Maintenance}.
     *
     * @param daysSinceMaintenanceString Days since last maintenance.
     */
    public Maintenance(String daysSinceMaintenanceString) {
        requireNonNull(daysSinceMaintenanceString);
        checkArgument(isValidMaintenance(daysSinceMaintenanceString), MESSAGE_MAINTENANCE_CONSTRAINTS);
        int value = Integer.parseInt(daysSinceMaintenanceString);
        lastMaintenanceDate = LocalDate.now().minusDays((long)value);
    }

    /**
     * Constructs a {@code Maintenance}.
     * @param daysSinceMaintenance Days since last maintenance.
     */
    public Maintenance(int daysSinceMaintenance) {
        lastMaintenanceDate = LocalDate.now().minusDays(daysSinceMaintenance);
    }

    public int getValue() {
        Period period = lastMaintenanceDate.until(LocalDate.now());
        return period.getDays();
    }

    public void setValue(int value) {
        this.lastMaintenanceDate = LocalDate.now().minusDays((long)value);
    }

    /**
     * Returns true if a given string is a valid days since last maintenance.
     */
    public static boolean isValidMaintenance(String test) {
        return test.matches(MAINTENANCE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(getValue()) + " days";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Maintenance // instanceof handles nulls
            && lastMaintenanceDate == ((Maintenance) other).lastMaintenanceDate); // state check
    }

    @Override
    public int hashCode() {
        return getValue();
    }

}
