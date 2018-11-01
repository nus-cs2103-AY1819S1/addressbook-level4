package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.MeetingBookChangedEvent;
import seedu.address.commons.events.model.MeetingBookExportEvent;
import seedu.address.commons.events.model.UserPrefsChangeEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMeetingBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of MeetingBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MeetingBookStorage meetingBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(MeetingBookStorage meetingBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.meetingBookStorage = meetingBookStorage;
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



    // ================ MeetingBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return meetingBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyMeetingBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(meetingBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyMeetingBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return meetingBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyMeetingBook addressBook) throws IOException {
        saveAddressBook(addressBook, meetingBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyMeetingBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        meetingBookStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void deleteAddressBook(Path filePath) throws IOException {
        logger.fine("Deleting addressbook at: " + filePath);
        meetingBookStorage.deleteAddressBook(filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(MeetingBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleAddressBookExportEvent(MeetingBookExportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Exporting data and saving to file"));
        try {
            saveAddressBook(event.data, event.path);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleUserPrefsChangeEvent(UserPrefsChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Updating User Prefs"));
        try {
            saveAddressBook(event.data, event.newPath);
            deleteAddressBook(event.oldPath);
            saveUserPrefs(event.userPrefs);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
