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
import seedu.address.model.test.ReadOnlyTriviaResults;

/**
 * A class to access TriviaResults data stored as an xml file on the hard disk.
 */
public class XmlTriviaResultsStorage implements TriviaResultsStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlTriviaResultsStorage.class);

    private Path filePath;

    public XmlTriviaResultsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTriviaResultsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTriviaResults> readTriviaResults() throws DataConversionException, IOException {
        return readTriviaResults(filePath);
    }

    /**
     * Similar to {@link #readTriviaResults()} ()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTriviaResults> readTriviaResults(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("TriviaResults file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableTriviaResults xmlTriviaResult = XmlFileStorage.loadDataFromSaveFile(filePath,
                XmlSerializableTriviaResults.class);
        try {
            return Optional.of(xmlTriviaResult.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTriviaResults(ReadOnlyTriviaResults triviaResults) throws IOException {
        saveTriviaResults(triviaResults, filePath);
    }

    /**
     * Similar to {@link #saveTriviaResults(ReadOnlyTriviaResults)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTriviaResults(ReadOnlyTriviaResults triviaResults, Path filePath) throws IOException {
        requireNonNull(triviaResults);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableTriviaResults(triviaResults));
    }
}
