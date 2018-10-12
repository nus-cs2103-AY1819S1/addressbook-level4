package seedu.learnvocabulary.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.learnvocabulary.commons.core.LogsCenter;
import seedu.learnvocabulary.commons.exceptions.DataConversionException;
import seedu.learnvocabulary.commons.exceptions.IllegalValueException;
import seedu.learnvocabulary.commons.util.FileUtil;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;

/**
 * A class to access LearnVocabulary data stored as an xml file on the hard disk.
 */
public class XmlLearnVocabularyStorage implements LearnVocabularyStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlLearnVocabularyStorage.class);

    private Path filePath;
    private Path backupFilePath;


    public XmlLearnVocabularyStorage(Path filePath) {
        this.filePath = filePath;
        this.backupFilePath = Paths.get(filePath.toString() + ".backup");
    }

    public Path getLearnVocabularyFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLearnVocabulary> readLearnVocabulary() throws DataConversionException, IOException {
        return readLearnVocabulary(filePath);
    }

    /**
     * Similar to {@link #readLearnVocabulary()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLearnVocabulary> readLearnVocabulary(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("LearnVocabulary file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableLearnVocabulary xmlLearnVocabulary = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlLearnVocabulary.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void backupLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary) throws IOException {
        saveLearnVocabulary(learnVocabulary, backupFilePath);
    }

    @Override
    public void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary) throws IOException {
        saveLearnVocabulary(learnVocabulary, filePath);
    }

    /**
     * Similar to {@link #saveLearnVocabulary(ReadOnlyLearnVocabulary)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveLearnVocabulary(ReadOnlyLearnVocabulary learnVocabulary, Path filePath) throws IOException {
        requireNonNull(learnVocabulary);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableLearnVocabulary(learnVocabulary));
    }

}
