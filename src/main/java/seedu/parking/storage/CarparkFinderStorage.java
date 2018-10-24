package seedu.parking.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.parking.commons.exceptions.DataConversionException;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.ReadOnlyCarparkFinder;

/**
 * Represents a storage for {@link CarparkFinder}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns CarparkFinder data as a {@link ReadOnlyCarparkFinder}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCarparkFinder> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyCarparkFinder> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCarparkFinder} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyCarparkFinder addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyCarparkFinder)
     */
    void saveAddressBook(ReadOnlyCarparkFinder addressBook, Path filePath) throws IOException;

}
