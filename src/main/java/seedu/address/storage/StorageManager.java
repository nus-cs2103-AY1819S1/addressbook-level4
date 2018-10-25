package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.AddressbookComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AnakinChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAnakin;
import seedu.address.model.UserPrefs;

/**
 * Manages anakinStorage of Anakin data in local anakinStorage.
 */
public class StorageManager extends AddressbookComponentManager implements AnakinStorage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private Storage storage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(Storage storage, UserPrefsStorage userPrefsStorage) {
        super();
        this.storage = storage;
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


    // ================ Anakin methods ==============================

    @Override
    public Path getAnakinFilePath() {
        return storage.getAnakinFilePath();
    }

    @Override
    public Optional<ReadOnlyAnakin> readAnakin() throws DataConversionException, IOException {
        return readAnakin(storage.getAnakinFilePath());
    }

    @Override
    public Optional<ReadOnlyAnakin> readAnakin(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return storage.readAnakin(filePath);
    }

    @Override
    public void saveAnakin(ReadOnlyAnakin anakin) throws IOException {
        saveAnakin(anakin, storage.getAnakinFilePath());
    }

    @Override
    public void saveAnakin(ReadOnlyAnakin anakin, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        storage.saveAnakin(anakin, filePath);
    }


    @Override
    @Subscribe
    public void handleAnakinChangedEvent(AnakinChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAnakin(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
