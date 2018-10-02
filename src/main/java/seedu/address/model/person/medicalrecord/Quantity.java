package seedu.address.model.person.medicalrecord;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.medicine.Stock;

public class Quantity {

    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Quantity should only contain numbers";
    public static final String QUANTITY_VALIDATION_REGEX = "\\d{0,}";
    public final String value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param number A valid number for quantity.
     */
    public Quantity(String number) {
        requireNonNull(number);
        checkArgument(isValidQuantity(number), MESSAGE_QUANTITY_CONSTRAINTS);
        value = number;
    }

    /**
     * Returns true if a given string is a valid number.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value.equals(((Quantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
