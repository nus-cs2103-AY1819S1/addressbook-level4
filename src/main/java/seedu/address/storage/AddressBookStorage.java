package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySchedulePlanner;
import seedu.address.model.SchedulePlanner;

/**
 * Represents a storage for {@link SchedulePlanner}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns SchedulePlanner data as a {@link ReadOnlySchedulePlanner}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySchedulePlanner> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlySchedulePlanner> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySchedulePlanner} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlySchedulePlanner addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlySchedulePlanner)
     */
    void saveAddressBook(ReadOnlySchedulePlanner addressBook, Path filePath) throws IOException;

}
