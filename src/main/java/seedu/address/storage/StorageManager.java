package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of TaskManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskManagerStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskManagerStorage taskManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = taskManagerStorage;
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


    // ================ TaskManager methods ==============================

    @Override
    public Path getTaskManagerFilePath() {
        return addressBookStorage.getTaskManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(addressBookStorage.getTaskManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readTaskManager(filePath);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, addressBookStorage.getTaskManagerFilePath());
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveTaskManager(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleTaskManagerChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
