package seedu.parking.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.parking.commons.core.ComponentManager;
import seedu.parking.commons.core.LogsCenter;
import seedu.parking.commons.events.model.CarparkFinderChangedEvent;
import seedu.parking.commons.events.storage.DataSavingExceptionEvent;
import seedu.parking.commons.exceptions.DataConversionException;
import seedu.parking.model.ReadOnlyCarparkFinder;
import seedu.parking.model.UserPrefs;

/**
 * Manages storage of CarparkFinder data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CarparkFinderStorage carparkFinderStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CarparkFinderStorage carparkFinderStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.carparkFinderStorage = carparkFinderStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CarparkFinder methods ==============================

    @Override
    public Path getCarparkFinderFilePath() {
        return carparkFinderStorage.getCarparkFinderFilePath();
    }

    @Override
    public Optional<ReadOnlyCarparkFinder> readCarparkFinder() throws DataConversionException, IOException {
        return readCarparkFinder(carparkFinderStorage.getCarparkFinderFilePath());
    }

    @Override
    public Optional<ReadOnlyCarparkFinder> readCarparkFinder(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return carparkFinderStorage.readCarparkFinder(filePath);
    }

    @Override
    public void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder) throws IOException {
        saveCarparkFinder(carparkFinder, carparkFinderStorage.getCarparkFinderFilePath());
    }

    @Override
    public void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        carparkFinderStorage.saveCarparkFinder(carparkFinder, filePath);
    }


    @Override
    @Subscribe
    public void handleCarparkFinderChangedEvent(CarparkFinderChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveCarparkFinder(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
