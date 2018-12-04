package seedu.thanepark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.thanepark.commons.exceptions.DataConversionException;
import seedu.thanepark.model.ReadOnlyThanePark;
import seedu.thanepark.model.ThanePark;

/**
 * Represents a storage for {@link ThanePark}.
 */
public interface ThaneParkStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getThaneParkFilePath();

    /**
     * Returns ThanePark data as a {@link ReadOnlyThanePark}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyThanePark> readThanePark() throws DataConversionException, IOException;

    /**
     * @see #getThaneParkFilePath()
     */
    Optional<ReadOnlyThanePark> readThanePark(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyThanePark} to the storage.
     * @param thanePark cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveThanePark(ReadOnlyThanePark thanePark) throws IOException;

    /**
     * @see #saveThanePark(ReadOnlyThanePark)
     */
    void saveThanePark(ReadOnlyThanePark thanePark, Path filePath) throws IOException;

}
