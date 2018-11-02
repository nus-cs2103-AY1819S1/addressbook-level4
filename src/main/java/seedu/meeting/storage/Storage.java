package seedu.meeting.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.meeting.commons.events.model.MeetingBookChangedEvent;
import seedu.meeting.commons.events.model.MeetingBookExportEvent;
import seedu.meeting.commons.events.model.UserPrefsChangeEvent;
import seedu.meeting.commons.events.storage.DataSavingExceptionEvent;
import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.model.ReadOnlyMeetingBook;
import seedu.meeting.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MeetingBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getMeetingBookFilePath();

    @Override
    Optional<ReadOnlyMeetingBook> readMeetingBook() throws DataConversionException, IOException;

    @Override
    void saveMeetingBook(ReadOnlyMeetingBook meetingBook) throws IOException;

    /**
     * Saves the current version of the MeetingBook to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleMeetingBookChangedEvent(MeetingBookChangedEvent abce);

    /**
     * Export the current version of the MeetingBook to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleMeetingBookExportEvent(MeetingBookExportEvent abce);

    /**
     * Change the storage location of the current version of the MeetingBook.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleUserPrefsChangeEvent(UserPrefsChangeEvent abce);
}
