package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AppContentChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAppContent;
import seedu.address.model.UserPrefs;

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
    public Path getFilePath() {
        return genericStorage.getFilePath();
    }

    @Override
    public Optional<ReadOnlyAppContent> read() throws DataConversionException, IOException {
        return read(genericStorage.getFilePath());
    }

    @Override
    public Optional<ReadOnlyAppContent> read(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return genericStorage.read(filePath);
    }

    @Override
    public void save(ReadOnlyAppContent appContent) throws IOException {
        save(appContent, genericStorage.getFilePath());
    }

    @Override
    public void save(ReadOnlyAppContent appContent, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        genericStorage.save(appContent, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AppContentChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            save(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }


}
