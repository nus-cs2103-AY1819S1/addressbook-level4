package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.awareness.Awareness;

/**
 * Represents storage for the Awareness object. Provides a high-level API to read the Awareness data.
 */
public interface AwarenessStorage {

    Path AWARENESS_FILEPATH = Paths.get("awareness.xml");

    /**
     * Returns Awareness data wrapped in an Optional.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws FileNotFoundException if the awareness file was not found.
     */
    Optional<Awareness> readAwarenessData() throws DataConversionException, FileNotFoundException;

}
