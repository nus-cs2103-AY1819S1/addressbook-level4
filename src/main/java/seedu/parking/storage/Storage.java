package seedu.parking.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.parking.commons.events.model.CarparkFinderChangedEvent;
import seedu.parking.commons.events.storage.DataSavingExceptionEvent;
import seedu.parking.commons.exceptions.DataConversionException;
import seedu.parking.model.ReadOnlyCarparkFinder;
import seedu.parking.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CarparkFinderStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getCarparkFinderFilePath();

    @Override
    Optional<ReadOnlyCarparkFinder> readCarparkFinder() throws DataConversionException, IOException;

    @Override
    void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder) throws IOException;

    /**
     * Saves the current version of the Car Park Finder to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleCarparkFinderChangedEvent(CarparkFinderChangedEvent abce);
}
