package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyArchiveList;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface ArchiveListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getArchiveListFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyArchiveList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyArchiveList> readArchiveList() throws DataConversionException, IOException;

    /**
     * @see #getArchiveListFilePath()
     */
    Optional<ReadOnlyArchiveList> readArchiveList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyArchiveList} to the storage.
     * @param archiveList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveArchiveList(ReadOnlyArchiveList archiveList) throws IOException;

    /**
     * @see #saveArchiveList(ReadOnlyArchiveList)
     */
    void saveArchiveList(ReadOnlyArchiveList archiveList, Path filePath) throws IOException;

}
