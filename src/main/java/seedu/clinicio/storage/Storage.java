package seedu.clinicio.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinicio.commons.events.model.ClinicIoChangedEvent;
import seedu.clinicio.commons.events.storage.DataSavingExceptionEvent;
import seedu.clinicio.commons.exceptions.DataConversionException;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ClinicIoStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getClinicIoFilePath();

    @Override
    Optional<ReadOnlyClinicIo> readClinicIo() throws DataConversionException, IOException;

    @Override
    void saveClinicIo(ReadOnlyClinicIo clinicIo) throws IOException;

    /**
     * Saves the current version of the ClinicIO to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleClinicIoChangedEvent(ClinicIoChangedEvent abce);
}
