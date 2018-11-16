package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySchedule;

/**
 * Represents a storage for {@link seedu.address.model.Schedule}.
 */
public interface ScheduleStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getScheduleFilePath();

    /**
     * Returns Schedule data as a {@link ReadOnlySchedule}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySchedule> readSchedule() throws DataConversionException, IOException, ParseException;

    /**
     * @see #getScheduleFilePath()
     */
    Optional<ReadOnlySchedule> readSchedule(Path filePath) throws DataConversionException, IOException, ParseException;

    /**
     * Saves the given {@link ReadOnlySchedule} to the storage.
     * @param schedule cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSchedule(ReadOnlySchedule schedule) throws IOException;

    /**
     * @see #saveSchedule(ReadOnlySchedule)
     */
    void saveSchedule(ReadOnlySchedule schedule, Path filePath) throws IOException;

}
