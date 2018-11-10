package seedu.restaurant.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

import java.util.Objects;

//@@author rebstan97
/**
 * Represents the minimum threshold in terms of number of available units for an Ingredient in the restaurant
 * management app to be considered "low in stock count".
 * Guarantees: immutable; is valid as declared in {@link #isValidMinimum(String)} and
 * {@link #isValidMinimum(int)}
 */
public class MinimumUnit {

    public static final String MESSAGE_MINIMUM_CONSTRAINTS =
            "Minimum unit should only contain numeric characters, and it should not be blank";

    /*
     * The first character of the price must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MINIMUM_VALIDATION_REGEX = "^\\d+$";

    private final int minimumUnit;

    /**
     * Constructs a {@code MinimumUnit}.
     *
     * @param minimum A valid minimum number of units.
     */
    public MinimumUnit(int minimum) {
        requireNonNull(minimum);
        checkArgument(isValidMinimum(minimum), MESSAGE_MINIMUM_CONSTRAINTS);
        minimumUnit = minimum;
    }

    /**
     * Returns true if a given string is a valid minimum number of units.
     */
    public static boolean isValidMinimum(String test) {
        return test.matches(MINIMUM_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given integer is a valid minimum number of units.
     */
    public static boolean isValidMinimum(int test) {
        return test >= 0;
    }

    /**
     * Returns the value of {@code minimumUnit}.
     */
    public int getMinimumUnit() {
        return minimumUnit;
    }

    @Override
    public String toString() {
        return String.valueOf(minimumUnit);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MinimumUnit // instanceof handles nulls
                    && minimumUnit == (((MinimumUnit) other).minimumUnit)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimumUnit);
    }
}
