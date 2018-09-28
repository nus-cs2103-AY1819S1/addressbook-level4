package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the unit price of a medicine in the clinic.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)} and {@link #isValidUnit(String)}
 */
public class PricePerUnit {
    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Minimum Stock Quantity should be integers.";
    public static final String MESSAGE_UNIT_CONSTRAINTS =
            "Unit should be in pill or bottle.";
    public static final String PRICE_VALIDATION_REGEX = "^[0-9]+([\\,|\\.]{0,1}[0-9]{2}){0,1}$";
    public static final String[] UNIT_VALIDATION_REGEX = new String[] {"pill", "bottle"};
    public final String value;
    public final String unit;

    /**
     * Constructs a {@code PricePerUnit}.
     *
     * @param number A positive integer.
     * @param measurement A valid unit of measurement.
     */
    public PricePerUnit(String number, String measurement) {
        requireNonNull(number);
        requireNonNull(measurement);
        checkArgument(isValidPrice(number), MESSAGE_PRICE_CONSTRAINTS);
        checkArgument(isValidUnit(measurement), MESSAGE_UNIT_CONSTRAINTS);
        value = number;
        unit = measurement;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid unit.
     */
    public static boolean isValidUnit(String test) {
        return test.equals(UNIT_VALIDATION_REGEX[0]) || test.equals(UNIT_VALIDATION_REGEX[1]);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PricePerUnit // instanceof handles nulls
                && value.equals(((PricePerUnit) other).value)
                && unit.equals(((PricePerUnit) other).unit)); // state check
    }

    @Override
    public int hashCode() {
        return (value + unit).hashCode();
    }

}
