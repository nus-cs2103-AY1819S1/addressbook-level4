package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import org.simplejavamail.email.Email;

public interface EmailStorage {

    /**
     * Returns the file path of the Email directory.
     */
    Path getEmailPath();

    /**
     * Saves the given Email to the storage.
     * @param email cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(Email email) throws IOException;
    
}
