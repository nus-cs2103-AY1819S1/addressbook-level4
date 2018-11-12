package seedu.address.model.accounting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Class representing the debt amount.
 * Guarantees: immutable.
 */
public class Amount {

    public static final String MESSAGE_AMOUNT_CONSTRAINTS =
            "Amount should only contain numbers up to 2 decimal places.";

    public static final String AMOUNT_VALIDATION_REGEX = "\\d+(\\.\\d{1,2})?";

    private final double amount;

    public Amount(String amt) {
        requireNonNull(amt);
        checkArgument(isValidAmount(amt), MESSAGE_AMOUNT_CONSTRAINTS);
        amount = Double.valueOf(amt).doubleValue();
    }

    /**
     * Returns the amount as a double.
     */
    public double toDouble() {
        return amount;
    }

    /**
     * Returns the amount as a string.
     */
    public String toString() {
        return String.valueOf(amount);
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(AMOUNT_VALIDATION_REGEX);
    }

    /**
     * Returns true if the object is the same object as this,
     * or they have the same amount(double).
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount == ((Amount) other).amount); // state check

    }

}
