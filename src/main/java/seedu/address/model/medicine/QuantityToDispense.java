package seedu.address.model.medicine;

/**
 * Represent the quantity of a medicine to be dispensed.
 */
public class QuantityToDispense {
    public static final String MESSAGE_QUANTITY_TO_DISPENSE_CONSTRAINTS =
            "Quantity to dispense should be a positive integer.";
    public final Integer value;

    /**
     * Constructs a {@code QuantityToDispense}
     *
     * @param value A positive integer.
     */
    public QuantityToDispense(Integer value) {
        this.value = value;
    }

    /**
     * Returns true if a given quantity is a valid quantity to dispense
     */
    public static boolean isValidQuantityToDispense(String test) {
        return Integer.parseInt(test) > 0;
    }

    /**
     * Getter for the value of the quantity to dispense.
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
                || (other instanceof QuantityToDispense
                && value.equals(((QuantityToDispense) other).getValue()));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
