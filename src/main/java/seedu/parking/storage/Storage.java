package seedu.parking.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.parking.commons.events.model.AddressBookChangedEvent;
import seedu.parking.commons.events.storage.DataSavingExceptionEvent;
import seedu.parking.commons.exceptions.DataConversionException;
import seedu.parking.model.ReadOnlyAddressBook;
import seedu.parking.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Car Park Finder to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
