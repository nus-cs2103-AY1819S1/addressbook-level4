package seedu.learnvocabulary.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.learnvocabulary.commons.events.model.LearnVocabularyChangedEvent;
import seedu.learnvocabulary.commons.events.storage.DataSavingExceptionEvent;
import seedu.learnvocabulary.commons.exceptions.DataConversionException;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;
import seedu.learnvocabulary.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends LearnVocabularyStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getLearnVocabularyFilePath();

    @Override
    Optional<ReadOnlyLearnVocabulary> readLearnVocabulary() throws DataConversionException, IOException;

    @Override
    void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary) throws IOException;

    /**
     * Saves the current version of the LearnVocabulary to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleLearnVocabularyChangedEvent(LearnVocabularyChangedEvent abce);
}
