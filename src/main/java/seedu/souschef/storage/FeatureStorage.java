package seedu.souschef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;

/**
 * Represents a storage for {@link AppContent}.
 */
public interface FeatureStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFeatureFilePath();

    /**
     * Returns AppContent data as a {@link ReadOnlyAppContent}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAppContent> readAppContent() throws DataConversionException, IOException;

    /**
     * @see #getFeatureFilePath()
     */
    Optional<ReadOnlyAppContent> readAppContent(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAppContent} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppContent(ReadOnlyAppContent addressBook) throws IOException;

    /**
     * @see #saveAppContent(ReadOnlyAppContent)
     */

    void saveAppContent(ReadOnlyAppContent addressBook, Path filePath) throws IOException;







}
