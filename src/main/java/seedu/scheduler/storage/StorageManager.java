package seedu.scheduler.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.scheduler.commons.core.ComponentManager;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.events.model.SchedulerChangedEvent;
import seedu.scheduler.commons.events.storage.DataSavingExceptionEvent;
import seedu.scheduler.commons.exceptions.DataConversionException;
import seedu.scheduler.model.ReadOnlyScheduler;
import seedu.scheduler.model.UserPrefs;

/**
 * Manages storage of Scheduler data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SchedulerStorage schedulerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(SchedulerStorage schedulerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.schedulerStorage = schedulerStorage;
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

    // ================ Scheduler methods ==============================

    @Override
    public Path getSchedulerFilePath() {
        return schedulerStorage.getSchedulerFilePath();
    }

    @Override
    public Optional<ReadOnlyScheduler> readScheduler() throws DataConversionException, IOException {
        return readScheduler(schedulerStorage.getSchedulerFilePath());
    }

    @Override
    public Optional<ReadOnlyScheduler> readScheduler(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from scheduler storage file: " + filePath);
        return schedulerStorage.readScheduler(filePath);
    }

    @Override
    public void saveScheduler(ReadOnlyScheduler scheduler) throws IOException {
        saveScheduler(scheduler, schedulerStorage.getSchedulerFilePath());
    }

    @Override
    public void saveScheduler(ReadOnlyScheduler scheduler, Path filePath) throws IOException {
        logger.fine("Attempting to write to scheduler data file: " + filePath);
        schedulerStorage.saveScheduler(scheduler, filePath);
    }

    @Override
    @Subscribe
    public void handleSchedulerChangedEvent(SchedulerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveScheduler(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
