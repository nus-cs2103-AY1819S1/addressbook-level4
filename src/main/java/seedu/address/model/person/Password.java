package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.Serializable;

/**
 * Represents a Person's password in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password implements Serializable {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
        "Passwords should contain at least 8 characters. It should contain at least 1 lowercase character, "
            + "1 uppercase character and 1 number.";

    /*
     * The password regex expression to test for the constraints stated in MESSAGE_PASSWORD_CONSTRAINTS
     */
    public static final String PASSWORD_VALIDATION_REGEX = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";

    public static final String DEFAULT_PASSWORD = "Pa55w0rd";

    public final String password;

    /**
     * Constructs a {@code Password} containing the default password.
     */
    public Password() {
        password = DEFAULT_PASSWORD;
    }

    /**
     * Constructs a {@code Password}.
     *
     * @param pass A valid password.
     */
    public Password(String pass) {
        requireNonNull(pass);
        checkArgument(isValidPassword(pass), MESSAGE_PASSWORD_CONSTRAINTS);
        password = pass;
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        boolean answer = test.matches(PASSWORD_VALIDATION_REGEX);
        return answer;
    }


    @Override
    public String toString() {
        return password;
    }

    public String getEncodedPassword() {
        return password;
    }

    /**
     * Checks if this password matches a certain password in plaintext
     * @param plaintext The entered password in plaintext
     * @return if it matches are not
     */
    public boolean matches(String plaintext) {
        return password.equals(plaintext);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Password // instanceof handles nulls
            && password.equals(((Password) other).password)); // state check
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }

}
