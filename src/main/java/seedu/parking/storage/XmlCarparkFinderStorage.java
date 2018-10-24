package seedu.parking.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.parking.commons.core.LogsCenter;
import seedu.parking.commons.exceptions.DataConversionException;
import seedu.parking.commons.exceptions.IllegalValueException;
import seedu.parking.commons.util.FileUtil;
import seedu.parking.model.ReadOnlyCarparkFinder;

/**
 * A class to access CarparkFinder data stored as an xml file on the hard disk.
 */
public class XmlCarparkFinderStorage implements CarparkFinderStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCarparkFinderStorage.class);

    private Path filePath;

    public XmlCarparkFinderStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCarparkFinderFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCarparkFinder> readCarparkFinder() throws DataConversionException, IOException {
        return readCarparkFinder(filePath);
    }

    /**
     * Similar to {@link #readCarparkFinder()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCarparkFinder> readCarparkFinder(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("CarparkFinder file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableCarparkFinder xmlCarparkFinder = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlCarparkFinder.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder) throws IOException {
        saveCarparkFinder(carparkFinder, filePath);
    }

    /**
     * Similar to {@link #saveCarparkFinder(ReadOnlyCarparkFinder)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveCarparkFinder(ReadOnlyCarparkFinder carparkFinder, Path filePath) throws IOException {
        requireNonNull(carparkFinder);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableCarparkFinder(carparkFinder));
    }

}
