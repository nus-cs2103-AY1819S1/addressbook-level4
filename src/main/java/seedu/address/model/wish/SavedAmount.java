package seedu.address.model.wish;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

import seedu.address.commons.core.amount.Amount;

/**
 * Represents a saved Amount for a Wish in the wish book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSavedAmount(String)}
 */
public class SavedAmount {
    public static final String MESSAGE_SAVED_AMOUNT_INVALID = "Invalid saved amount value!";
    public static final String MESSAGE_SAVED_AMOUNT_NEGATIVE = "Invalid. Saved amount causes current value to "
            + "become negative";
    public static final String MESSAGE_SAVED_AMOUNT_TOO_LARGE = "Current saved amount for wish is too large!";
    public static final String SAVED_AMOUNT_VALIDATION_REGEX = "[-+]?[0-9]+([.]{1}[0-9]{1,2})?";
    public final Double value;
    private DecimalFormat currencyFormat = new DecimalFormat("#.##");

    /**
     * Constructs a {@code SavedAmount}.
     *
     * @param savedAmount A valid savedAmount number.
     */
    public SavedAmount(String savedAmount) throws IllegalArgumentException {
        requireNonNull(savedAmount);
        checkArgument(isValidSavedAmount(savedAmount), MESSAGE_SAVED_AMOUNT_INVALID);
        value = Double.parseDouble(savedAmount);
        if (value.doubleValue() < 0) {
            throw new IllegalArgumentException(MESSAGE_SAVED_AMOUNT_NEGATIVE);
        } else if (value.doubleValue() > 1000e12) {
            throw new IllegalArgumentException(MESSAGE_SAVED_AMOUNT_TOO_LARGE);
        }
    }

    /**
     * Constructs a {@code SavedAmount} from an increment {@code Amount}.
     *
     * @param change A valid {@code Amount} to increment this {@code SavedAmount}.
     * @return Incremented {@code SavedAmount}.
     */
    public SavedAmount incrementSavedAmount(Amount change) {
        if (this.value + change.value < 0.0) {
            throw new IllegalArgumentException(MESSAGE_SAVED_AMOUNT_NEGATIVE);
        }

        return new SavedAmount(currencyFormat.format(this.value + change.value));
    }

    /**
     * Returns a {@code Amount} from the savedAmount.
     */
    public Amount getAmount() {
        return new Amount("" + this.value);
    }

    /**
     * Returns true if a given string is a valid savedAmount number.
     */
    public static boolean isValidSavedAmount(String test) {
        return test.matches(SAVED_AMOUNT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SavedAmount // instanceof handles nulls
                && value.equals(((SavedAmount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
