package seedu.jxmusic.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.jxmusic.commons.core.ComponentManager;
import seedu.jxmusic.commons.core.LogsCenter;
import seedu.jxmusic.commons.events.model.LibraryChangedEvent;
import seedu.jxmusic.commons.events.storage.DataSavingExceptionEvent;
import seedu.jxmusic.commons.exceptions.DataConversionException;
import seedu.jxmusic.model.ReadOnlyLibrary;
import seedu.jxmusic.model.UserPrefs;

/**
 * Manages storage of Library data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private LibraryStorage libraryStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(LibraryStorage libraryStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.libraryStorage = libraryStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Library methods ==============================

    @Override
    public Path getLibraryFilePath() {
        return libraryStorage.getLibraryFilePath();
    }

    @Override
    public ReadOnlyLibrary readLibrary() throws DataConversionException, IOException {
        return readLibrary(libraryStorage.getLibraryFilePath());
    }

    @Override
    public ReadOnlyLibrary readLibrary(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return libraryStorage.readLibrary(filePath);
    }

    @Override
    public void saveLibrary(ReadOnlyLibrary library) throws IOException {
        saveLibrary(library, libraryStorage.getLibraryFilePath());
    }

    @Override
    public void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        libraryStorage.saveLibrary(library, filePath);
    }


    @Override
    @Subscribe
    public void handleLibraryChangedEvent(LibraryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveLibrary(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
