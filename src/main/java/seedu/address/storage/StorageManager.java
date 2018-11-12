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
import seedu.address.commons.events.model.TriviaResultsChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.ReadOnlyTriviaResults;

/**
 * Manages storage of AddressBook and TriviaBundle data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    public static final String MESSAGE_DATA_FILE_NOT_FOUND = "Data file not found. Will be starting with a sample %s";
    public static final String MESSAGE_PROBLEM_READING_FILE = "Problem while reading from the file. "
                + "Will be starting with an empty %s";
    public static final String MESSAGE_INCORRECT_DATA_FILE = "Data file not in the correct format. "
            + "Will be starting with an empty %s";

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private TriviaBundleStorage triviaBundleStorage;
    private UserPrefsStorage userPrefsStorage;
    private TriviaResultsStorage triviaResultsStorage;

    public StorageManager(AddressBookStorage addressBookStorage, TriviaBundleStorage triviaBundleStorage,
                          TriviaResultsStorage triviaResultsStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.triviaResultsStorage = triviaResultsStorage;
        this.addressBookStorage = addressBookStorage;
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

    // ================ TriviaTest Results methods ==============================

    @Override
    public Path getTriviaResultsFilePath() {
        return triviaResultsStorage.getTriviaResultsFilePath();
    }

    @Override
    public Optional<ReadOnlyTriviaResults> readTriviaResults() throws DataConversionException, IOException {
        return readTriviaResults(triviaResultsStorage.getTriviaResultsFilePath());
    }

    @Override
    public Optional<ReadOnlyTriviaResults> readTriviaResults(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from results file: " + filePath);
        return triviaResultsStorage.readTriviaResults(filePath);
    }

    @Override
    public void saveTriviaResults(ReadOnlyTriviaResults triviaResults) throws IOException {
        saveTriviaResults(triviaResults, triviaResultsStorage.getTriviaResultsFilePath());
    }

    @Override
    public void saveTriviaResults(ReadOnlyTriviaResults triviaResults, Path filePath) throws IOException {
        logger.fine("Attempting to write to trivia test results file " + filePath);
        triviaResultsStorage.saveTriviaResults(triviaResults, filePath);
    }

    @Override
    @Subscribe
    public void handleTriviaResultsChangedEvent(TriviaResultsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTriviaResults(event.results);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
