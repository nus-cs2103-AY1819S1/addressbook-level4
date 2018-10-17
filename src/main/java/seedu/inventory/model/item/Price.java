package seedu.inventory.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents an Item's price in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {


    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price should only contain numbers and/or a decimal point, and it should be at least 1 digit long. "
            + "Price can only contain a maximum of 8 digits before the decimal point, "
            + "and 2 digits after the decimal point.";

    /*
     * The first character of the item's price must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PRICE_VALIDATION_REGEX = "^\\d{1,8}(\\.\\d{1,2})?$";
    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        value = String.format("%.2f", Double.parseDouble(price));
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
