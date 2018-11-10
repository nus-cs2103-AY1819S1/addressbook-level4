package seedu.restaurant.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of available units of an Ingredient.
 * Guarantees: immutable; is valid as declared in {@link #isValidNumUnits(String) and  {@link #isValidNumUnits(int)}}
 */
public class NumUnits {

    public static final String MESSAGE_NUM_UNITS_CONSTRAINTS =
            "Number of units should only contain numeric characters, and it should not be blank";

    /*
     * The first character of the price must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NUM_UNITS_VALIDATION_REGEX = "^\\d+$";

    private final int numberOfUnits;

    /**
     * Constructs a {@code NumUnits}.
     *
     * @param numUnits A valid number of available units.
     */
    public NumUnits(int numUnits) {
        requireNonNull(numUnits);
        checkArgument(isValidNumUnits(numUnits), MESSAGE_NUM_UNITS_CONSTRAINTS);
        numberOfUnits = numUnits;
    }

    /**
     * Returns true if a given int is a valid number of available units.
     */
    public static boolean isValidNumUnits(int test) {
        return test >= 0;
    }

    /**
     * Returns true if a given string is a valid number of available units.
     */
    public static boolean isValidNumUnits(String test) {
        return test.matches(NUM_UNITS_VALIDATION_REGEX);
    }

    /**
     * Returns the value of {@code numberOfUnits}.
     */
    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    @Override
    public String toString() {
        return String.valueOf(numberOfUnits);
    }

    /**
     * Increases the number of units by {@code toIncrease}.
     * @return A new {@code NumUnits} object with the updated value.
     */
    public NumUnits increase(int toIncrease) {
        int updatedNum = numberOfUnits + toIncrease;
        return new NumUnits(updatedNum);
    }

    /**
     * Decreases the number of units by {@code toDecrease}.
     * @return A new {@code NumUnits} object with the updated value.
     */
    public NumUnits decrease(int toDecrease) {
        assert(toDecrease <= numberOfUnits);
        int updatedNum = numberOfUnits - toDecrease;
        return new NumUnits(updatedNum);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumUnits // instanceof handles nulls
                && numberOfUnits == ((NumUnits) other).getNumberOfUnits()); // state check
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(numberOfUnits).hashCode();
    }
}
