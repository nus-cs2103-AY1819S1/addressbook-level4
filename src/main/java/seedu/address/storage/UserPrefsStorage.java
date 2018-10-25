package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserPrefs;

/**
 * Represents a anakinStorage for {@link seedu.address.model.UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Returns UserPrefs data from anakinStorage.
     *   Returns {@code Optional.empty()} if anakinStorage file is not found.
     * @throws DataConversionException if the data in anakinStorage is not in the expected format.
     * @throws IOException if there was any problem when reading from the anakinStorage.
     */
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.UserPrefs} to the anakinStorage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

}
