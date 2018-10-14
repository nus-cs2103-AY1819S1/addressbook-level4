package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserData;

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
    Optional<UserData> readUserData() throws DataConversionException, IOException;

    /**
     * @see #getUsersFilePath()
     */
    Optional<UserData> readUserData(Path filePath) throws DataConversionException, IOException;

    /**
     * @param userData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserData(UserData userData) throws IOException;

    void saveUserData(UserData userData, Path filePath) throws IOException;
}
