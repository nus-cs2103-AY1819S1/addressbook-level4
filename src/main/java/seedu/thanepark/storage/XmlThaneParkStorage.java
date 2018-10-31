package seedu.thanepark.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.thanepark.commons.core.LogsCenter;
import seedu.thanepark.commons.exceptions.DataConversionException;
import seedu.thanepark.commons.exceptions.IllegalValueException;
import seedu.thanepark.commons.util.FileUtil;
import seedu.thanepark.model.ReadOnlyThanePark;

/**
 * A class to access ThanePark data stored as an xml file on the hard disk.
 */
public class XmlThaneParkStorage implements ThaneParkStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlThaneParkStorage.class);

    private Path filePath;

    public XmlThaneParkStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getThaneParkFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyThanePark> readThanePark() throws DataConversionException, IOException {
        return readThanePark(filePath);
    }

    /**
     * Similar to {@link #readThanePark()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyThanePark> readThanePark(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ThanePark file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableThanePark xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveThanePark(ReadOnlyThanePark addressBook) throws IOException {
        saveThanePark(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveThanePark(ReadOnlyThanePark)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveThanePark(ReadOnlyThanePark addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableThanePark(addressBook));
    }

}
