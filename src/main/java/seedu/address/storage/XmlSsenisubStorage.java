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
import seedu.address.model.ReadOnlySsenisub;

/**
 * A class to access SSENISUB data stored as an xml file on the hard disk.
 */
public class XmlSsenisubStorage implements SsenisubStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlSsenisubStorage.class);

    private Path filePath;

    public XmlSsenisubStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSsenisubFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySsenisub> readSsenisub() throws DataConversionException, IOException {
        return readSsenisub(filePath);
    }

    /**
     * Similar to {@link #readSsenisub()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySsenisub> readSsenisub(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("SSENISUB file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableSsenisub xmlSsenisub = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlSsenisub.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSsenisub(ReadOnlySsenisub ssenisub) throws IOException {
        saveSsenisub(ssenisub, filePath);
    }

    /**
     * Similar to {@link #saveSsenisub(ReadOnlySsenisub)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSsenisub(ReadOnlySsenisub ssenisub, Path filePath) throws IOException {
        requireNonNull(ssenisub);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableSsenisub(ssenisub));
    }

}
