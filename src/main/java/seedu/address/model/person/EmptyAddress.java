package seedu.address.model.person;

/**
 * Represents an empty address.
 * Guarantees: immutable;
 */
public class EmptyAddress extends Address {

    public final String value;

    /**
     * Constructs an empty {@code Address}.
     */
    public EmptyAddress() {
        this.value = "";
    }

    /**
     * Returns true so that storage won't see EmptyAddress objects as errors.
     */
    public static boolean isValidAddress(String test) {
        if (test.equals("")) {
            return true;
        } else {
            return Address.isValidAddress(test);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Address // instanceof handles nulls
            && value.equals(((Address) other).value)); // state check
    }
}
