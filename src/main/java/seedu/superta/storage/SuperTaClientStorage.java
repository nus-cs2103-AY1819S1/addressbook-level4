package seedu.superta.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.superta.commons.exceptions.DataConversionException;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.SuperTaClient;

/**
 * Represents a storage for {@link SuperTaClient}.
 */
public interface SuperTaClientStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSuperTaClientFilePath();

    /**
     * Returns SuperTaClient data as a {@link ReadOnlySuperTaClient}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySuperTaClient> readSuperTaClient() throws DataConversionException, IOException;

    /**
     * @see #getSuperTaClientFilePath()
     */
    Optional<ReadOnlySuperTaClient> readSuperTaClient(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySuperTaClient} to the storage.
     * @param superTaClient cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSuperTaClient(ReadOnlySuperTaClient superTaClient) throws IOException;

    /**
     * @see #saveSuperTaClient(ReadOnlySuperTaClient)
     */
    void saveSuperTaClient(ReadOnlySuperTaClient superTaClient, Path filePath) throws IOException;

}
