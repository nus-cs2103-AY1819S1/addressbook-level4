package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.HealthBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyHealthBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of HealthBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HealthBookStorage healthBookStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(HealthBookStorage healthBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.healthBookStorage = healthBookStorage;
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


    // ================ HealthBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return healthBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyHealthBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(healthBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyHealthBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return healthBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyHealthBook addressBook) throws IOException {
        saveAddressBook(addressBook, healthBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyHealthBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        healthBookStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(HealthBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
