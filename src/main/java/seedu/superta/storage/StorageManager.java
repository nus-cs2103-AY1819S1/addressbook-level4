package seedu.superta.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.superta.commons.core.ComponentManager;
import seedu.superta.commons.core.LogsCenter;
import seedu.superta.commons.events.model.SuperTaClientChangedEvent;
import seedu.superta.commons.events.storage.DataSavingExceptionEvent;
import seedu.superta.commons.exceptions.DataConversionException;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.UserPrefs;

/**
 * Manages storage of SuperTaClient data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SuperTaClientStorage superTaClientStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(SuperTaClientStorage superTaClientStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.superTaClientStorage = superTaClientStorage;
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


    // ================ SuperTaClient methods ==============================

    @Override
    public Path getSuperTaClientFilePath() {
        return superTaClientStorage.getSuperTaClientFilePath();
    }

    @Override
    public Optional<ReadOnlySuperTaClient> readSuperTaClient() throws DataConversionException, IOException {
        return readSuperTaClient(superTaClientStorage.getSuperTaClientFilePath());
    }

    @Override
    public Optional<ReadOnlySuperTaClient> readSuperTaClient(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return superTaClientStorage.readSuperTaClient(filePath);
    }

    @Override
    public void saveSuperTaClient(ReadOnlySuperTaClient superTaClient) throws IOException {
        saveSuperTaClient(superTaClient, superTaClientStorage.getSuperTaClientFilePath());
    }

    @Override
    public void saveSuperTaClient(ReadOnlySuperTaClient superTaClient, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        superTaClientStorage.saveSuperTaClient(superTaClient, filePath);
    }


    @Override
    @Subscribe
    public void handleSuperTaClientChangedEvent(SuperTaClientChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveSuperTaClient(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
