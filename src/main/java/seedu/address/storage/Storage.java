package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AppContentChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAppContent;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends GenericStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getFilePath();

    @Override
    Optional<ReadOnlyAppContent> read() throws DataConversionException, IOException;

    @Override
    void save(ReadOnlyAppContent appContent) throws IOException;


    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AppContentChangedEvent abce);
}
