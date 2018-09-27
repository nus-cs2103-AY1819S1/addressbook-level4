package seedu.address.model.doctor;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import at.favre.lib.crypto.bcrypt.BCrypt.Verifyer;

//@@author jjlee050
/**
 * Represents a Doctor's password in the ClinicIO.
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {
    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PASSWORD_VALIDATION_REGEX = "[\\p{Alnum}]{6,12}";

    public final String password;

    /**
     * Constructs a {@code Password}.
     *
     * @param password A valid password.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.password = password;
    }    
    
    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    //@@author jjlee050-reused
    //Reused from https://github.com/patrickfav/bcrypt with minor modifications
    /**
     * Returns true if the given hashed password match the user hash password.
     */
    public static boolean isSameAsHashPassword(String password, String passwordHashString) {
        Verifyer verifyer = BCrypt.verifyer();
        Result result = verifyer.verify(password.toCharArray(), passwordHashString);
        return result.verified;
        
    }
    
    @Override
    public String toString() {
        return password;
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
