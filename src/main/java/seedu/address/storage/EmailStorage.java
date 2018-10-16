package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.model.EmailModel;

//@@author EatOrBeEaten
/**
 * Represents a storage for Email.
 */
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
    void saveEmail(EmailModel email) throws IOException;

}
