package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the unit price of a medicine in the clinic.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)} and {@link #isValidUnit(String)}
 */
public class PricePerUnit {
    public static final String MESSAGE_PRICE_PER_UNIT_CONSTRAINTS =
            "Price per unit should be an integer.";
    public static final String PRICE_PER_UNIT_VALIDATION_REGEX = "^[0-9]+([\\,|\\.]{0,1}[0-9]{2}){0,1}$";
    public final String value;

    /**
     * Constructs a {@code PricePerUnit}.
     *
     * @param number A positive integer.
     */
    public PricePerUnit(String number) {
        requireNonNull(number);
        checkArgument(isValidPricePerUnit(number), MESSAGE_PRICE_PER_UNIT_CONSTRAINTS);
        value = number;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPricePerUnit(String test) {
        return test.matches(PRICE_PER_UNIT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PricePerUnit // instanceof handles nulls
                && value.equals(((PricePerUnit) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
