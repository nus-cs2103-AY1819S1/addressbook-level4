package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ClinicIo;
import seedu.address.model.ReadOnlyClinicIo;

/**
 * Represents a storage for {@link ClinicIo}.
 */
public interface ClinicIoStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns ClinicIo data as a {@link ReadOnlyClinicIo}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyClinicIo> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyClinicIo> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyClinicIo} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyClinicIo addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyClinicIo)
     */
    void saveAddressBook(ReadOnlyClinicIo addressBook, Path filePath) throws IOException;

}
