package seedu.address.commons.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import at.favre.lib.crypto.bcrypt.BCrypt.Verifyer;
import at.favre.lib.crypto.bcrypt.BCrypt.Version;

//@@author jjlee050
/**
 * Helper functions to maintain hashing password.
 */
public class HashUtil {
    private static final int COST = 12;
    
    /**
     * Return the Bcrypt hashing version.
     */
    private static Hasher getVersion() {
        return BCrypt.with(Version.VERSION_2B);
    }

    /**
     * Return a hashed password string from a valid password.
     * @param password A valid password
     */
    public static String hashToString(String password) {
        return getVersion().hashToString(COST, password.toCharArray());
    }

    /**
     * Return true if the password matches the hashed password.
     * @param password A valid password
     * @param passwordHashString The hashed password
     */
    public static boolean verifyPassword(String password, String passwordHashString) {
        Verifyer verifyer = BCrypt.verifyer();
        Result result = verifyer.verify(password.toCharArray(), passwordHashString);
        return result.verified;
    }
}
