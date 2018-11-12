package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.test.ReadOnlyTriviaResults;
import seedu.address.model.test.TriviaResults;

/**
 * Represents a storage for {@link TriviaResults}.
 */
public interface TriviaResultsStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getTriviaResultsFilePath();

    /**
     * Returns TriviaResults data as a {@link ReadOnlyTriviaResults}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTriviaResults> readTriviaResults() throws DataConversionException, IOException;

    /**
     * @see #getTriviaResultsFilePath() ()
     */
    Optional<ReadOnlyTriviaResults> readTriviaResults(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTriviaResults} to the storage.
     * @param triviaResults cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTriviaResults(ReadOnlyTriviaResults triviaResults) throws IOException;

    /**
     * @see #saveTriviaResults(ReadOnlyTriviaResults)
     */
    void saveTriviaResults(ReadOnlyTriviaResults triviaResults, Path filePath) throws IOException;
}
