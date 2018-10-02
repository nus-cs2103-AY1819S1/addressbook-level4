package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Represents a storage for Users.
 */
public interface UsersStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUsersFilePath();

    /**
     * Returns Users data as a HashMap.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<HashMap<Username, User>> readUsers() throws DataConversionException, IOException;

    /**
     * @see #getUsersFilePath()
     */
    Optional<HashMap<Username, User>> readUsers(Path filePath) throws DataConversionException, IOException;

    /**
     * @param usernameUserHashMap cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUsers(HashMap<Username, User> usernameUserHashMap) throws IOException;

    void saveUsers(HashMap<Username, User> usernameUserHashMap, Path filePath) throws IOException;
}
