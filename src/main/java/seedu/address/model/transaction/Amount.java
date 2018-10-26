package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ericyjw
/**
 * Represents a transaction entry amount in the Cca book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 *
 * @author ericyjw
 */
public class Amount {
    public static final String MESSAGE_AMOUNT_CONSTRAINTS =
        "Transaction amount should only contain digits and dashes, and it should not be blank";
    /*
     * The first character of the Date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String AMOUNT_VALIDATION_REGEX = "[-][0-9]{1,9}||[0-9]{1,9}";

    private Integer amount;

    public Amount(Integer amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(String.valueOf(amount)), MESSAGE_AMOUNT_CONSTRAINTS);
        this.amount = amount;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public static boolean isValidAmount(String test) {
        return test.matches(AMOUNT_VALIDATION_REGEX);
    }

    public static boolean isValidAmount(Entry e) {
        String amount = String.valueOf(e.getAmountValue());
        return amount.matches(AMOUNT_VALIDATION_REGEX);
    }

    /**
     * Returns true if both amounts are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Amount)) {
            return false;
        }

        Amount otherAmounts = (Amount) other;
        return otherAmounts.amount.equals(this.amount);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
