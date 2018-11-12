package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.SsenisubChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySsenisub;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SsenisubStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getSsenisubFilePath();

    @Override
    Optional<ReadOnlySsenisub> readSsenisub() throws DataConversionException, IOException;

    @Override
    void saveSsenisub(ReadOnlySsenisub ssenisub) throws IOException;

    /**
     * Saves the current version of Ssenisub to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSsenisubChangedEvent(SsenisubChangedEvent abce);
}
