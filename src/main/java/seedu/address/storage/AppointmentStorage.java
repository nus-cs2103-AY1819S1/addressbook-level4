package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.appointment.Appointment;

/**
 * Represents a storage for {@link seedu.address.model.appointment.Appointment}.
 */
public interface AppointmentStorage {

    /**
     * Returns the file path of the AppointmentStorage data file.
     */
    Path getAppointmentStorageFilePath();

    /**
     * Returns AppointmentStorage data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Appointment> readAppointmentStorage() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.appointment.Appointment} to the storage.
     * @param AppointmentStorage cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppointmentStorage(Appointment AppointmentStorage) throws IOException;

}
