package seedu.learnvocabulary.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.learnvocabulary.commons.exceptions.DataConversionException;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;

/**
 * Represents a storage for {@link LearnVocabulary}.
 */
public interface LearnVocabularyStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLearnVocabularyFilePath();

    /**
     * Returns LearnVocabulary data as a {@link ReadOnlyLearnVocabulary}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLearnVocabulary> readLearnVocabulary() throws DataConversionException, IOException;

    /**
     * @see #getLearnVocabularyFilePath()
     */
    Optional<ReadOnlyLearnVocabulary> readLearnVocabulary(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLearnVocabulary} to the storage.
     * @param learnVocabulary cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary) throws IOException;

    /**
     * @see #saveLearnVocabulary(ReadOnlyLearnVocabulary)
     */
    void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary, Path filePath) throws IOException;

    /**
     * Saves a backup of {@link ReadOnlyLearnVocabulary} to the storage.
     * @throws IOException if there was any problem writing to the file.
     */

    void backupLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary) throws IOException;


}
