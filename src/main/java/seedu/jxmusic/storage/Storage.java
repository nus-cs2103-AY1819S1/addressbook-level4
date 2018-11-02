package seedu.jxmusic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jxmusic.commons.events.model.LibraryChangedEvent;
import seedu.jxmusic.commons.events.storage.DataSavingExceptionEvent;
import seedu.jxmusic.commons.exceptions.DataConversionException;
import seedu.jxmusic.model.ReadOnlyLibrary;
import seedu.jxmusic.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends LibraryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getLibraryFilePath();

    @Override
    ReadOnlyLibrary readLibrary() throws DataConversionException, IOException;

    @Override
    void saveLibrary(ReadOnlyLibrary library) throws IOException;

    /**
     * Saves the current version of the Library to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleLibraryChangedEvent(LibraryChangedEvent lce);
}
