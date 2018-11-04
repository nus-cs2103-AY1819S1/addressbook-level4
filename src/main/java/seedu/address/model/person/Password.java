package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
    public static final int ITERATIONS = 10;
    public static final int LENGTH = 256;

    private static final Random RANDOM = new SecureRandom();

    public final String plaintext;
    public final byte[] salt;
    public final String hash;

    /**
     * Constructs a {@code Password} containing the default password.
     */
    public Password() {
        this(DEFAULT_PASSWORD);
    }

    /**
     * Constructs a {@code Password}.
     *
     * @param pass A valid plaintext.
     */
    public Password(String pass) {
        requireNonNull(pass);
        checkArgument(isValidPassword(pass), MESSAGE_PASSWORD_CONSTRAINTS);
        plaintext = pass;
        salt = generateSalt();
        hash = hash(plaintext, salt);
    }

    public Password(byte[] salt, String hash) {
        requireNonNull(salt);
        requireNonNull(hash);
        plaintext = null;
        this.salt = salt;
        this.hash = hash;
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        boolean answer = test.matches(PASSWORD_VALIDATION_REGEX);
        return answer;
    }

    /**
     * Generates the salt to be used with the password
     * @return the salt
     */
    public static byte[] generateSalt() {
        byte[] salt = new byte[LENGTH];
        RANDOM.nextBytes(salt);
        return salt;
    }


    /**
     * Hashes a password with a specific salt
     * @param s the password to hash, in plaintext
     * @param salt The salt to add
     * @return The salted and hashed password.
     */
    public static String hash(String s, byte[] salt) {
        char[] password = s.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, LENGTH);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] result = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Password hashing error: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }


    @Override
    public String toString() {
        return plaintext;
    }

    public String getEncodedPassword() {
        return plaintext;
    }

    /**
     * Checks if this password matches a certain password in plaintext
     * @param plaintext The entered password in plaintext
     * @return if it matches are not
     */
    public boolean matches(String plaintext) {
        String hashed = hash(plaintext, salt);
        return hashed.equals(hash);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Password // instanceof handles nulls
            && plaintext.equals(((Password) other).plaintext)); // state check
    }

    @Override
    public int hashCode() {
        return plaintext.hashCode();
    }

}
