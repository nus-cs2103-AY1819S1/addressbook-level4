package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ExpensesStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getExpensesFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readExpenses() throws DataConversionException, IOException;

    /**
     * Returns all AddressBook data as a map with String keys and {@link ReadOnlyAddressBook} values. Data is read
     * from the input dirPath.
     * Creates the directory at the given path if does not exists.
     * @param dirPath cannot be null
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Map<String, ReadOnlyAddressBook> readAllExpenses(Path dirPath) throws DataConversionException, IOException;

    @Override
    void saveExpenses(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
