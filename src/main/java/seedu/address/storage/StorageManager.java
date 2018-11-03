package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ArchivedListChangedEvent;
import seedu.address.commons.events.model.AssignmentListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyArchiveList;
import seedu.address.model.ReadOnlyAssignmentList;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private ArchiveListStorage archiveListStorage;
    private UserPrefsStorage userPrefsStorage;
    private AssignmentListStorage assignmentListStorage;


    public StorageManager(AddressBookStorage addressBookStorage, AssignmentListStorage assignmentListStorage,
                          ArchiveListStorage archiveListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.archiveListStorage = archiveListStorage;
        this.assignmentListStorage = assignmentListStorage;
    }

    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
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
    public Path getArchiveListFilePath() {
        return archiveListStorage.getArchiveListFilePath();
    }

    @Override
    public Optional<ReadOnlyArchiveList> readArchiveList() throws DataConversionException, IOException {
        return readArchiveList(archiveListStorage.getArchiveListFilePath());
    }

    @Override
    public Optional<ReadOnlyArchiveList> readArchiveList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return archiveListStorage.readArchiveList(filePath);
    }

    @Override
    public void saveArchiveList(ReadOnlyArchiveList archiveList) throws IOException {
        saveArchiveList(archiveList, archiveListStorage.getArchiveListFilePath());
    }

    @Override
    public void saveArchiveList(ReadOnlyArchiveList archiveList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        archiveListStorage.saveArchiveList(archiveList, filePath);
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

    // ================ AssignmentList methods ==============================

    @Override
    public Path getAssignmentListFilePath() {
        return assignmentListStorage.getAssignmentListFilePath();
    }

    @Override
    public Optional<ReadOnlyAssignmentList> readAssignmentList() throws DataConversionException, IOException {
        return readAssignmentList(assignmentListStorage.getAssignmentListFilePath());
    }

    @Override
    public Optional<ReadOnlyAssignmentList> readAssignmentList(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return assignmentListStorage.readAssignmentList(filePath);
    }

    @Override
    public void saveAssignmentList(ReadOnlyAssignmentList assignmentList) throws IOException {
        saveAssignmentList(assignmentList, assignmentListStorage.getAssignmentListFilePath());
    }

    @Override
    public void saveAssignmentList(ReadOnlyAssignmentList assignmentList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        assignmentListStorage.saveAssignmentList(assignmentList, filePath);
    }

    @Override
    @Subscribe
    public void handleAssignmentListChangedEvent(AssignmentListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAssignmentList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleArchivedListChangedEvent(ArchivedListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Archived data changed, saving to file"));
        try {
            saveArchiveList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
