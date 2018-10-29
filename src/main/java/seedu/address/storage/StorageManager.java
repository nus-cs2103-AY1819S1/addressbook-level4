package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ExpenseTrackerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyExpenseTracker;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Username;

/**
 * Manages storage of ExpenseTracker data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExpensesStorage expensesStorage;
    private UserPrefsStorage userPrefsStorage;
    private TipsStorage tipsStorage;


    public StorageManager(ExpensesStorage expensesStorage, UserPrefsStorage userPrefsStorage, TipsStorage tipsStorage) {
        super();
        this.expensesStorage = expensesStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.tipsStorage = tipsStorage;
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


    // ================ ExpenseTracker methods ==============================

    @Override
    public Path getExpensesDirPath() {
        return expensesStorage.getExpensesDirPath();
    }

    @Override
    public Optional<ReadOnlyExpenseTracker> readExpenses() throws DataConversionException, IOException {
        return readExpenses(expensesStorage.getExpensesDirPath());
    }

    @Override
    public Optional<ReadOnlyExpenseTracker> readExpenses(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return expensesStorage.readExpenses(filePath);
    }

    @Override
    public Map<Username, ReadOnlyExpenseTracker> readAllExpenses(Path dirPath) throws DataConversionException,
            IOException {
        File dir = new File(dirPath.toString());
        final Map<Username, ReadOnlyExpenseTracker> books = new TreeMap<>();
        File[] directoryListing = dir.listFiles();
        if (!dir.mkdir()) {
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    readExpenses(Paths.get(child.getPath())).ifPresent(
                        expenseTracker -> books.put(new Username(child.getName().replace(".xml", "")),
                                expenseTracker));
                }
            }
        }
        return books;
    }

    @Override
    public void saveExpenses(ReadOnlyExpenseTracker expenseTracker) throws IOException {
        Path path = Paths.get(expensesStorage.getExpensesDirPath().toString(),
                expenseTracker.getUsername().toString() + ".xml");
        saveExpenses(expenseTracker, path);
    }

    @Override
    public void saveExpenses(ReadOnlyExpenseTracker expenseTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        expensesStorage.saveExpenses(expenseTracker, filePath);
    }

    @Override
    public void backupExpenses(ReadOnlyExpenseTracker expenseTracker) throws IOException {
        backupExpenses(expenseTracker, expensesStorage.getExpensesDirPath());
    }

    @Override
    public void backupExpenses(ReadOnlyExpenseTracker expenseTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write backup of: " + filePath);
        expensesStorage.backupExpenses(expenseTracker, filePath);
    }

    @Override
    @Subscribe
    public void handleExpenseTrackerChangedEvent(ExpenseTrackerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveExpenses(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
