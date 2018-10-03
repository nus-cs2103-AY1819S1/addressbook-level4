package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AppContent;
import seedu.address.model.ReadOnlyAppContent;

/**
 * Represents a storage for {@link AppContent}.
 */
public interface GenericStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFilePath();

    /**
     * Returns AppContent data as a {@link ReadOnlyAppContent}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAppContent> read() throws DataConversionException, IOException;

    /**
     * @see #getFilePath()
     */
    Optional<ReadOnlyAppContent> read(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAppContent} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void save(ReadOnlyAppContent addressBook) throws IOException;

    /**
     * @see #save(ReadOnlyAppContent)
     */

    void save(ReadOnlyAppContent addressBook, Path filePath) throws IOException;







}
