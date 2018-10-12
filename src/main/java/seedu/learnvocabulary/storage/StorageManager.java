package seedu.learnvocabulary.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.learnvocabulary.commons.core.ComponentManager;
import seedu.learnvocabulary.commons.core.LogsCenter;
import seedu.learnvocabulary.commons.events.model.LearnVocabularyChangedEvent;
import seedu.learnvocabulary.commons.events.storage.DataSavingExceptionEvent;
import seedu.learnvocabulary.commons.exceptions.DataConversionException;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;
import seedu.learnvocabulary.model.UserPrefs;

/**
 * Manages storage of LearnVocabulary data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private LearnVocabularyStorage learnVocabularyStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(LearnVocabularyStorage learnVocabularyStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.learnVocabularyStorage = learnVocabularyStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ LearnVocabulary methods ==============================

    @Override
    public Path getLearnVocabularyFilePath() {
        return learnVocabularyStorage.getLearnVocabularyFilePath();
    }

    @Override
    public Optional<ReadOnlyLearnVocabulary> readLearnVocabulary() throws DataConversionException, IOException {
        return readLearnVocabulary(learnVocabularyStorage.getLearnVocabularyFilePath());
    }

    @Override
    public Optional<ReadOnlyLearnVocabulary> readLearnVocabulary(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return learnVocabularyStorage.readLearnVocabulary(filePath);
    }

    @Override
    public void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary) throws IOException {
        saveLearnVocabulary(learnVocabulary, learnVocabularyStorage.getLearnVocabularyFilePath());
    }

    @Override
    public void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        learnVocabularyStorage.saveLearnVocabulary(learnVocabulary, filePath);
    }

    @Override
    public void backupLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary) throws IOException {
        learnVocabularyStorage.backupLearnVocabulary(learnVocabulary);
    }

    @Override
    @Subscribe
    public void handleLearnVocabularyChangedEvent(LearnVocabularyChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveLearnVocabulary(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
