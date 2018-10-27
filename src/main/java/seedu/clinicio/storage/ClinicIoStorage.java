package seedu.clinicio.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.clinicio.commons.exceptions.DataConversionException;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.ReadOnlyClinicIo;

/**
 * Represents a storage for {@link ClinicIo}.
 */
public interface ClinicIoStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getClinicIoFilePath();

    /**
     * Returns ClinicIo data as a {@link ReadOnlyClinicIo}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyClinicIo> readClinicIo() throws DataConversionException, IOException;

    /**
     * @see #getClinicIoFilePath()
     */
    Optional<ReadOnlyClinicIo> readClinicIo(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyClinicIo} to the storage.
     * @param clinicIo cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveClinicIo(ReadOnlyClinicIo clinicIo) throws IOException;

    /**
     * @see #saveClinicIo(ReadOnlyClinicIo)
     */
    void saveClinicIo(ReadOnlyClinicIo addressBook, Path filePath) throws IOException;

}
