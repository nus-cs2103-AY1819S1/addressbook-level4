package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TriviaBundleChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook and TriviaBundle data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private TriviaBundleStorage triviaBundleStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(AddressBookStorage addressBookStorage, TriviaBundleStorage triviaBundleStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.triviaBundleStorage = triviaBundleStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(TriviaBundleStorage triviaBundleStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.triviaBundleStorage = triviaBundleStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ TriviaBundle methods ==============================
    @Override
    public Path getTriviaBundleFilePath() {
        return triviaBundleStorage.getTriviaBundleFilePath();
    }

    @Override
    public Optional<ReadOnlyTriviaBundle> readTriviaBundle() throws DataConversionException, IOException {
        return readTriviaBundle(triviaBundleStorage.getTriviaBundleFilePath());
    }

    @Override
    public Optional<ReadOnlyTriviaBundle> readTriviaBundle(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return triviaBundleStorage.readTriviaBundle(filePath);
    }

    @Override
    public void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle) throws IOException {
        saveTriviaBundle(triviaBundle, triviaBundleStorage.getTriviaBundleFilePath());
    }

    @Override
    public void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        triviaBundleStorage.saveTriviaBundle(triviaBundle, filePath);
    }


    @Override
    @Subscribe
    public void handleTriviaBundleChangedEvent(TriviaBundleChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTriviaBundle(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
