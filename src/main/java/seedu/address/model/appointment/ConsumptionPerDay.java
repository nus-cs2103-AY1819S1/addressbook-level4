package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents number of consumption per day in prescription
 */
public class ConsumptionPerDay {
    public static final String MESSAGE_CONSTRAINTS =
            "ConsumptionPerDay should only contain numbers, and it must be greater than 0";
    public static final String CONSUMPTION_PER_DAY_VALIDATION_REGEX = "^[1-9][0-9]*$";
    private final String value;

    /**
     * Constructs a {@code consumptionPerDay}.
     *
     * @param consumptionPerDay A valid consumptionPerDay
     */
    public ConsumptionPerDay(String consumptionPerDay) {
        requireNonNull(consumptionPerDay);
        checkArgument(isValidConsumptionPerDay(consumptionPerDay), MESSAGE_CONSTRAINTS);
        value = consumptionPerDay;
    }

    public String getValue() {
        return value;
    }

    public ConsumptionPerDay() {
        value = "";
    }

    /**
     * Returns true if a given string is a valid number of consumption per day
     */
    public static boolean isValidConsumptionPerDay(String test) {
        return test.matches(CONSUMPTION_PER_DAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConsumptionPerDay // instanceof handles nulls
                && value.equals(((ConsumptionPerDay) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
