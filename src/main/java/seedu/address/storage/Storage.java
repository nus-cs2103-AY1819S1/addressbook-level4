package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TriviaBundleChangedEvent;
import seedu.address.commons.events.model.TriviaResultsChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.ReadOnlyTriviaResults;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, TriviaBundleStorage, TriviaResultsStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Path getTriviaBundleFilePath();

    @Override
    Path getTriviaResultsFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyTriviaBundle> readTriviaBundle() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyTriviaResults> readTriviaResults() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle) throws IOException;

    @Override
    void saveTriviaResults(ReadOnlyTriviaResults triviaResults) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent event);

    /**
     * Saves the current version of the Trivia Bundle to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTriviaBundleChangedEvent(TriviaBundleChangedEvent event);

    /**
     * Saves the latest version of the TriviaResults to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTriviaResultsChangedEvent(TriviaResultsChangedEvent event);
}
