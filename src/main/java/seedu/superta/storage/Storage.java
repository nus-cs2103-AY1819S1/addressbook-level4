package seedu.superta.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.superta.commons.events.model.SuperTaClientChangedEvent;
import seedu.superta.commons.events.storage.DataSavingExceptionEvent;
import seedu.superta.commons.exceptions.DataConversionException;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SuperTaClientStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getSuperTaClientFilePath();

    @Override
    Optional<ReadOnlySuperTaClient> readSuperTaClient() throws DataConversionException, IOException;

    @Override
    void saveSuperTaClient(ReadOnlySuperTaClient superTaClient) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSuperTaClientChangedEvent(SuperTaClientChangedEvent abce);
}
