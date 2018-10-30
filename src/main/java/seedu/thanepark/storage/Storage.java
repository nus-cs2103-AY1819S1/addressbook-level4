package seedu.thanepark.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.thanepark.commons.events.model.ThaneParkChangedEvent;
import seedu.thanepark.commons.events.storage.DataSavingExceptionEvent;
import seedu.thanepark.commons.exceptions.DataConversionException;
import seedu.thanepark.model.ReadOnlyThanePark;
import seedu.thanepark.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ThaneParkStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyThanePark> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyThanePark addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(ThaneParkChangedEvent abce);
}
