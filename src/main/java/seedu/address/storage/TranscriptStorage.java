package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.model.Transcript;

import seedu.address.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@link seedu.address.model.Transcript}.
 */
public interface TranscriptStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTranscriptFilePath();

    /**
     * Returns Transcript data as a {@link Transcript}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Transcript> readTranscript() throws DataConversionException, IOException;

    /**
     * @see #getTranscriptFilePath()
     */
    Optional<Transcript> readTranscript(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link Transcript} to the storage.
     * @param transcript cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTranscript(Transcript transcript) throws IOException;

    /**
     * @see #saveTranscript(Transcript)
     */
    void saveTranscript(Transcript transcript, Path filePath) throws IOException;

}
