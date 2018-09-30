package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskCollectionChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskCollection;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of TaskCollection data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskCollectionStorage taskCollectionStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaskCollectionStorage taskCollectionStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.taskCollectionStorage = taskCollectionStorage;
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

    // ================ TaskCollection methods ==============================

    @Override
    public Path getTaskCollectionFilePath() {
        return taskCollectionStorage.getTaskCollectionFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskCollection> readTaskCollection()
        throws DataConversionException, IOException {
        return readTaskCollection(taskCollectionStorage.getTaskCollectionFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskCollection> readTaskCollection(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskCollectionStorage.readTaskCollection(filePath);
    }

    @Override
    public void saveTaskCollection(ReadOnlyTaskCollection taskCollection) throws IOException {
        saveTaskCollection(taskCollection, taskCollectionStorage.getTaskCollectionFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskCollection> importTaskCollection() throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public void exportTaskCollection(ReadOnlyTaskCollection taskCollection) throws IOException {

    }

    @Override
    public void saveTaskCollection(ReadOnlyTaskCollection addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskCollectionStorage.saveTaskCollection(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleTaskCollectionChangedEvent(TaskCollectionChangedEvent event) {
        logger.info(
            LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskCollection(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
