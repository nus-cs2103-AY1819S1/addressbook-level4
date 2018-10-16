package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.TriviaBundle;

/**
 * Represents a storage for {@link TriviaBundle}.
 */
public interface TriviaBundleStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTriviaBundleFilePath();

    /**
     * Returns TriviaBundle data as a {@link ReadOnlyTriviaBundle}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTriviaBundle> readTriviaBundle() throws DataConversionException, IOException;

    /**
     * @see #getTriviaBundleFilePath()
     */
    Optional<ReadOnlyTriviaBundle> readTriviaBundle(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTriviaBundle} to the storage.
     * @param triviaBundle cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle) throws IOException;

    /**
     * @see #saveTriviaBundle(ReadOnlyTriviaBundle)
     */
    void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle, Path filePath) throws IOException;
}
