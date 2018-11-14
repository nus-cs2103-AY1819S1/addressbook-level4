package seedu.address.model.person;

/**
 * Represents an empty email.
 * Guarantees: immutable;
 */
public class EmptyEmail extends Email {

    public final String value;

    /**
     * Constructs an empty {@code Email}.
     */
    public EmptyEmail() {
        this.value = "";
    }

    /**
     * Returns true so that storage won't see EmptyEmail objects as errors.
     */
    public static boolean isValidEmail(String test) {
        if (test.equals("")) {
            return true;
        } else {
            return Email.isValidEmail(test);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Email // instanceof handles nulls
            && value.equals(((Email) other).value)); // state check
    }
}
