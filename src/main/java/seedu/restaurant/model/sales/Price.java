package seedu.restaurant.model.sales;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

//@@author HyperionNKJ
/**
 * Represents the price of a menu item sold in the sales record
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price should be a positive number.";
    private final double value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price in string representation
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        value = Double.parseDouble(price);
    }

    public double getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        try {
            double price = Double.parseDouble(test);
            return price > 0.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                    && value == ((Price) other).value); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(value).hashCode();
    }
}
