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
    private FeatureStorage featureStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(FeatureStorage featureStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.featureStorage = featureStorage;
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
    public Path getFeatureFilePath() {
        return featureStorage.getFeatureFilePath();
    }

    @Override
    public Optional<ReadOnlyAppContent> readAppContent() throws DataConversionException, IOException {
        return readAppContent(featureStorage.getFeatureFilePath());
    }

    @Override
    public Optional<ReadOnlyAppContent> readAppContent(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to readAppContent data from file: " + filePath);
        return featureStorage.readAppContent(filePath);
    }

    @Override
    public void saveAppContent(ReadOnlyAppContent appContent) throws IOException {
        saveAppContent(appContent, featureStorage.getFeatureFilePath());
    }

    @Override
    public void saveAppContent(ReadOnlyAppContent appContent, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        featureStorage.saveAppContent(appContent, filePath);
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
