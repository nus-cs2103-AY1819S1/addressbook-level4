package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTriviaBundle;

/**
 * A class to access TriviaBundle data stored as an xml file on the hard disk.
 */
public class XmlTriviaBundleStorage implements TriviaBundleStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlTriviaBundleStorage.class);

    private Path filePath;

    public XmlTriviaBundleStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTriviaBundleFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTriviaBundle> readTriviaBundle() throws DataConversionException, IOException {
        return readTriviaBundle(filePath);
    }

    /**
     * Similar to {@link #readTriviaBundle()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTriviaBundle> readTriviaBundle(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("TriviaBundle file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableTriviaBundle xmlTriviaBundle = XmlFileStorage.loadDataFromSaveFile(filePath, true);
        try {
            return Optional.of(xmlTriviaBundle.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle) throws IOException {
        saveTriviaBundle(triviaBundle, filePath);
    }

    /**
     * Similar to {@link #saveTriviaBundle(ReadOnlyTriviaBundle)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTriviaBundle(ReadOnlyTriviaBundle triviaBundle, Path filePath) throws IOException {
        requireNonNull(triviaBundle);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableTriviaBundle(triviaBundle));
    }
}
