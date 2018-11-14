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
public interface CarparkFinderStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCarparkFinderFilePath();

    /**
     * Returns CarparkFinder data as a {@link ReadOnlyCarparkFinder}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCarparkFinder> readCarparkFinder() throws DataConversionException, IOException;

    /**
     * @see #getCarparkFinderFilePath()
     */
    Optional<ReadOnlyCarparkFinder> readCarparkFinder(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCarparkFinder} to the storage.
     * @param carparkFinder cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder) throws IOException;

    /**
     * @see #saveCarparkFinder(ReadOnlyCarparkFinder)
     */
    void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder, Path filePath) throws IOException;

}
