package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySsenisub;

/**
 * Represents a storage for {@link seedu.address.model.Ssenisub}.
 */
public interface SsenisubStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSsenisubFilePath();

    /**
     * Returns Ssenisub data as a {@link ReadOnlySsenisub}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySsenisub> readSsenisub() throws DataConversionException, IOException;

    /**
     * @see #getSsenisubFilePath()
     */
    Optional<ReadOnlySsenisub> readSsenisub(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySsenisub} to the storage.
     * @param ssenisub cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSsenisub(ReadOnlySsenisub ssenisub) throws IOException;

    /**
     * @see #saveSsenisub(ReadOnlySsenisub)
     */
    void saveSsenisub(ReadOnlySsenisub ssenisub, Path filePath) throws IOException;

}
