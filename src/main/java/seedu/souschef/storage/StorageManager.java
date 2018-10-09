package seedu.souschef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.souschef.commons.core.ComponentManager;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.model.AppContentChangedEvent;
import seedu.souschef.commons.events.storage.DataSavingExceptionEvent;
import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.UserPrefs;

/**
 * Manages storage of AppContent data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GenericStorage genericStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(GenericStorage genericStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.genericStorage = genericStorage;
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


    @Override
    public Path getAppContentFilePath() {
        return genericStorage.getAppContentFilePath();
    }

    @Override
    public Optional<ReadOnlyAppContent> readAppContent() throws DataConversionException, IOException {
        return readAppContent(genericStorage.getAppContentFilePath());
    }

    @Override
    public Optional<ReadOnlyAppContent> readAppContent(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to readAppContent data from file: " + filePath);
        return genericStorage.readAppContent(filePath);
    }

    @Override
    public void saveAppContent(ReadOnlyAppContent appContent) throws IOException {
        saveAppContent(appContent, genericStorage.getAppContentFilePath());
    }

    @Override
    public void saveAppContent(ReadOnlyAppContent appContent, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        genericStorage.saveAppContent(appContent, filePath);
    }


    @Override
    @Subscribe
    public void handleAppContentChangedEvent(AppContentChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAppContent(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }


}
