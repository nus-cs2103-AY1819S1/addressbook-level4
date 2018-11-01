package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.MeetingBookChangedEvent;
import seedu.address.commons.events.model.MeetingBookExportEvent;
import seedu.address.commons.events.model.UserPrefsChangeEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMeetingBook;
import seedu.address.model.UserPrefs;

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
