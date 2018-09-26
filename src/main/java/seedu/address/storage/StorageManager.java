package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Username;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExpensesStorage expensesStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ExpensesStorage expensesStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.expensesStorage = expensesStorage;
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
    public Path getExpensesFilePath() {
        return expensesStorage.getExpensesFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readExpenses() throws DataConversionException, IOException {
        return readExpenses(expensesStorage.getExpensesFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readExpenses(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return expensesStorage.readExpenses(filePath);
    }

    @Override
    public Map<Username, ReadOnlyAddressBook> readAllExpenses(Path dirPath) throws DataConversionException, IOException {
        File dir = new File(dirPath.toString());
        final Map<Username, ReadOnlyAddressBook> books = new TreeMap<>();
        File[] directoryListing = dir.listFiles();
        if (!dir.mkdir()) {
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    readExpenses(Paths.get(child.getPath())).ifPresent(
                            addressBook -> books.put(
                                    new Username(child
                                    .getName()
                                    .replace(".xml", "")),
                                    addressBook));
                }
            }
        }
        return books;
    }

    @Override
    public void saveExpenses(ReadOnlyAddressBook addressBook) throws IOException {
        saveExpenses(addressBook, expensesStorage.getExpensesFilePath());
    }

    @Override
    public void saveExpenses(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        expensesStorage.saveExpenses(addressBook, filePath);
    }

    @Override
    public void backupExpenses(ReadOnlyAddressBook addressBook) throws IOException {
        backupExpenses(addressBook, expensesStorage.getExpensesFilePath());
    }

    @Override
    public void backupExpenses(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write backup of: " + filePath);
        expensesStorage.backupExpenses(addressBook, filePath);
    }

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveExpenses(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
