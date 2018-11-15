package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;

/**
 * Represent the quantity of medicine to be restocked.
 */
public class AmountToRestock {

    public final Integer value;

    /**
     * Constructs a {@code AmountToRestock}
     *
     * @param value A positive integer.
     */
    public AmountToRestock(Integer value) {
        requireNonNull(value);
        this.value = value;
    }

    /**
     * Returns true if a given amount is a valid amount to restock.
     */
    public static boolean isValidAmountToRestock(AmountToRestock test) {
        return test.getValue() > 0;
    }

    /**
     * Returns true if a given amount is a valid amount to restock.
     */
    public static boolean isValidAmountToRestock(Integer test) {
        return test > 0;
    }

    /**
     * Getter for the value of the amount to restock.
     */
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AmountToRestock
                && value.equals(((AmountToRestock) other).getValue()));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
