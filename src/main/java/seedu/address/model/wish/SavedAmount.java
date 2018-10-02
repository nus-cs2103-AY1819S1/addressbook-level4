package seedu.address.model.wish;

import seedu.address.commons.core.amount.Amount;
import seedu.address.logic.commands.SaveCommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a saved Amount for a Wish in the wish book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSavedAmount(String)}
 */
public class SavedAmount {
    public static final String MESSAGE_SAVED_AMOUNT_CONSTRAINTS =
            "Invalid saved amount value!";
    public static final String MESSAGE_SAVED_AMOUNT_NEGATIVE = "Saved amount cannot be negative";
    public static final String SAVED_AMOUNT_VALIDATION_REGEX = "[-+]?[0-9]{1,14}([.]{1}[0-9]{1,2})?";
    public final Double value;

    /**
     * Constructs a {@code Price}.
     *
     * @param savedAmount A valid savedAmount number.
     */
    public SavedAmount(String savedAmount) throws IllegalArgumentException {
        requireNonNull(savedAmount);
        checkArgument(isValidSavedAmount(savedAmount), MESSAGE_SAVED_AMOUNT_CONSTRAINTS);
        value = Double.parseDouble(savedAmount);
        if(value.doubleValue() < 0) {
            throw new IllegalArgumentException(MESSAGE_SAVED_AMOUNT_NEGATIVE);
        }
    }

    /**
     * Constructs a {@code SavedAmount} from an increment {@code SavedAmount}.
     *
     * @param change A valid savedAmount to increment the current savedAmount with.
     */
    public SavedAmount incrementSavedAmount(Amount change) {
        return new SavedAmount("" + (this.value + change.value));
    }

    /**
     * Returns true if a given string is a valid phone number.
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
