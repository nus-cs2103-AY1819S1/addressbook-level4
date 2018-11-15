/* @@author 99percentile */
package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the minimum stock that is required in the clinic.
 * Guarantees: immutable; is valid as declared in {@link #isValidMinimumStockQuantity(Integer)}
 */
public class MinimumStockQuantity {
    public static final String MESSAGE_MINIMUM_STOCK_QUANTITY_CONSTRAINTS =
            "Minimum Stock Quantity should be integers.";
    public static final String MINIMUM_STOCK_QUANTITY_VALIDATION_REGEX = "^[1-9]+[0-9]*$";
    public final Integer value;

    /**
     * Constructs a {@code MinimumStockQuantity}.
     *
     * @param number A positive integer.
     */
    public MinimumStockQuantity(Integer number) {
        requireNonNull(number);
        checkArgument(isValidMinimumStockQuantity(number), MESSAGE_MINIMUM_STOCK_QUANTITY_CONSTRAINTS);
        value = number;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid integer.
     */
    public static boolean isValidMinimumStockQuantity(Integer test) {
        return test > 0;
    }


    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MinimumStockQuantity // instanceof handles nulls
                && value.equals(((MinimumStockQuantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
