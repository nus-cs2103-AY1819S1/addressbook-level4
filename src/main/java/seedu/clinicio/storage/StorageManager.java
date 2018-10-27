package seedu.clinicio.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.clinicio.commons.core.ComponentManager;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.model.ClinicIoChangedEvent;
import seedu.clinicio.commons.events.storage.DataSavingExceptionEvent;
import seedu.clinicio.commons.exceptions.DataConversionException;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.UserPrefs;

/**
 * Manages storage of ClinicIo data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ClinicIoStorage clinicIoStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(ClinicIoStorage clinicIoStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.clinicIoStorage = clinicIoStorage;
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


    // ================ ClinicIo methods ==============================

    @Override
    public Path getClinicIoFilePath() {
        return clinicIoStorage.getClinicIoFilePath();
    }

    @Override
    public Optional<ReadOnlyClinicIo> readClinicIo() throws DataConversionException, IOException {
        return readClinicIo(clinicIoStorage.getClinicIoFilePath());
    }

    @Override
    public Optional<ReadOnlyClinicIo> readClinicIo(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return clinicIoStorage.readClinicIo(filePath);
    }

    @Override
    public void saveClinicIo(ReadOnlyClinicIo clinicIo) throws IOException {
        saveClinicIo(clinicIo, clinicIoStorage.getClinicIoFilePath());
    }

    @Override
    public void saveClinicIo(ReadOnlyClinicIo addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        clinicIoStorage.saveClinicIo(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleClinicIoChangedEvent(ClinicIoChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveClinicIo(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
