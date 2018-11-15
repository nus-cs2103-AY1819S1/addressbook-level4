/* @@author 99percentile */
package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents the number of medicine in clinic.
 * Guarantees: immutable; is valid as declared in {@link #isValidStock(Integer)}
 */
public class Stock {
    public static final String MESSAGE_STOCK_CONSTRAINTS =
            "Stock should only contain numbers";
    public final Integer value;

    /**
     * Constructs a {@code Stock}.
     *
     * @param number A valid number for stock.
     */
    public Stock(Integer number) {
        requireNonNull(number);
        checkArgument(isValidStock(number), MESSAGE_STOCK_CONSTRAINTS);
        value = number;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid number.
     */
    public static boolean isValidStock(Integer test) {
        return test >= 0;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Stock // instanceof handles nulls
                && value.equals(((Stock) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

}
