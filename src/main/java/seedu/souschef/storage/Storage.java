package seedu.souschef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.souschef.commons.events.model.AppContentChangedEvent;
import seedu.souschef.commons.events.storage.DataSavingExceptionEvent;
import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends GenericStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAppContentFilePath();

    @Override
    Optional<ReadOnlyAppContent> readAppContent() throws DataConversionException, IOException;

    @Override
    void saveAppContent(ReadOnlyAppContent appContent) throws IOException;


    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAppContentChangedEvent(AppContentChangedEvent abce);
}
