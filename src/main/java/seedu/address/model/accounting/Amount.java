package seedu.address.model.accounting;

import static java.util.Objects.requireNonNull;

/**
 * Class representing the debt amount.
 */
public class Amount {

    public static final String MESSAGE_AMOUNT_CONSTRAINTS =
            "Amount should only contain numbers up to 2 decimal places.";
    public static final String AMOUNT_VALIDATION_REGEX = "\\d+(\\.\\d{1,2})?";
    private double amount;

    public Amount(String amount) {
        requireNonNull(amount);
        this.amount = Double.valueOf(amount);
    }

    public double toDouble() {
        return this.amount;
    }

    public static boolean isValidAmount(String test) {
        return test.matches(AMOUNT_VALIDATION_REGEX);
    }

}
