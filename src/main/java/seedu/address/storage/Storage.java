package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.SchedulePlannerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySchedulePlanner;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SchedulePlannerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getSchedulePlannerFilePath();

    @Override
    Optional<ReadOnlySchedulePlanner> readSchedulePlanner() throws DataConversionException, IOException;

    @Override
    void saveSchedulePlanner(ReadOnlySchedulePlanner schedulePlanner) throws IOException;

    /**
     * Saves the current version of the Schedule Planner to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSchedulePlannerChangedEvent(SchedulePlannerChangedEvent abce);
}
