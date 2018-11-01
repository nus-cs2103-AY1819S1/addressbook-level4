package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.MeetingBook;
import seedu.address.model.ReadOnlyMeetingBook;

/**
 * Represents a storage for {@link MeetingBook}.
 */
public interface MeetingBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns MeetingBook data as a {@link ReadOnlyMeetingBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMeetingBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyMeetingBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMeetingBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyMeetingBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyMeetingBook)
     */
    void saveAddressBook(ReadOnlyMeetingBook addressBook, Path filePath) throws IOException;

    /**
     * Delete MeetingBook in the storage.
     * @throws IOException if file is unable to be written.
     */
    void deleteAddressBook(Path filePath) throws IOException;
}
