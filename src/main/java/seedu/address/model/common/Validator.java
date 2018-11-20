package seedu.address.model.common;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Base class for all string validator.
 */
public abstract class Validator {

    public static final String VALIDATION_REGEX = "";

    public final String internalString;

    /**
     * Constructs a {@code Validator}.
     *
     * @param str A valid string.
     */
    public Validator(String str) {
        String temp = "";
        try {
            requireNonNull(str);
            checkArgument(
                    (boolean) this.getClass().getMethod("isValid", String.class).invoke(this, str),
                    (String) this.getClass().getField("MESSAGE_CONSTRAINTS").get(this)
            );
            temp = str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.internalString = temp;
    }

    /**
     * Returns true if a given string matches the child class VALIDATION_REGEX.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the internalString type casted into Object
     */
    public abstract Object getValue();

    @Override
    public String toString() {
        return this.internalString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (getClass() == other.getClass() // instanceof handles nulls
                && this.internalString.equals(((Validator) other).internalString)); // state check
    }

    @Override
    public int hashCode() {
        return this.internalString.hashCode();
    }

}
