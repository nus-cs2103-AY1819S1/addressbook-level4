package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.HealthBook;
import seedu.address.model.ReadOnlyHealthBook;

/**
 * Represents a storage for {@link HealthBook}.
 */
public interface HealthBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns HealthBook data as a {@link ReadOnlyHealthBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyHealthBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyHealthBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyHealthBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyHealthBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyHealthBook)
     */
    void saveAddressBook(ReadOnlyHealthBook addressBook, Path filePath) throws IOException;

}
