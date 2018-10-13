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

    AppContent getAppContent();

    Optional<ReadOnlyAppContent> readFeature() throws DataConversionException, IOException;

    /**
     * Returns AppContent data as a {@link ReadOnlyAppContent}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */

    /**
     * @see #getFeatureFilePath()
     */
    Optional<ReadOnlyAppContent> readFeature(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAppContent} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFeature(ReadOnlyAppContent addressBook) throws IOException;

    /**
     * @see #saveAppContent(ReadOnlyAppContent)
     */

    void saveFeature(ReadOnlyAppContent addressBook, Path filePath) throws IOException;







}
