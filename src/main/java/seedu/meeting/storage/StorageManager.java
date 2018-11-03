package seedu.meeting.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.meeting.commons.core.ComponentManager;
import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.commons.events.model.MeetingBookChangedEvent;
import seedu.meeting.commons.events.model.MeetingBookExportEvent;
import seedu.meeting.commons.events.model.UserPrefsChangeEvent;
import seedu.meeting.commons.events.storage.DataSavingExceptionEvent;
import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.model.ReadOnlyMeetingBook;
import seedu.meeting.model.UserPrefs;

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
    public Path getMeetingBookFilePath() {
        return meetingBookStorage.getMeetingBookFilePath();
    }

    @Override
    public Optional<ReadOnlyMeetingBook> readMeetingBook() throws DataConversionException, IOException {
        return readMeetingBook(meetingBookStorage.getMeetingBookFilePath());
    }

    @Override
    public Optional<ReadOnlyMeetingBook> readMeetingBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return meetingBookStorage.readMeetingBook(filePath);
    }

    @Override
    public void saveMeetingBook(ReadOnlyMeetingBook meetingBook) throws IOException {
        saveMeetingBook(meetingBook, meetingBookStorage.getMeetingBookFilePath());
    }

    @Override
    public void saveMeetingBook(ReadOnlyMeetingBook meetingBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        meetingBookStorage.saveMeetingBook(meetingBook, filePath);
    }

    @Override
    public void deleteMeetingBook(Path filePath) throws IOException {
        logger.fine("Deleting MeetingBook at: " + filePath);
        meetingBookStorage.deleteMeetingBook(filePath);
    }


    @Override
    @Subscribe
    public void handleMeetingBookChangedEvent(MeetingBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveMeetingBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleMeetingBookExportEvent(MeetingBookExportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Exporting data and saving to file"));
        try {
            saveMeetingBook(event.data, event.path);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleUserPrefsChangeEvent(UserPrefsChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Updating User Prefs"));
        try {
            saveMeetingBook(event.data, event.newPath);
            deleteMeetingBook(event.oldPath);
            saveUserPrefs(event.userPrefs);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
