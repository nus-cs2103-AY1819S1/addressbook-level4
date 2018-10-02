package seedu.address.commons.core.amount;

import static java.util.Objects.requireNonNull;

/**
 * Represents an amount of money. It can be negative.
 * Guarantees: immutable
 */
public class Amount {

    private static final String AMOUNT_REGEX = "[+-]?[0-9]{1,14}([.]{1}[0-9]{1,2})?";
    private final Double value;

    /**
     * Constructs a {@code Amount}.
     *
     * @param amount A valid amount {@code String}.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        if(isValidAmount(amount)) {
            value = Double.parseDouble(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if a given string is a valid Amount.
     *
     * @param possibleAmount A possibly valid Amount as a {@code String}
     */
    public static boolean isValidAmount(String possibleAmount) {
        return possibleAmount.matches(AMOUNT_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(!(other instanceof Amount)) {
            return false;
        }

        if(((Amount) other).value.equals(this.value)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
