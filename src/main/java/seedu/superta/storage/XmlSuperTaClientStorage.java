package seedu.superta.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.superta.commons.core.LogsCenter;
import seedu.superta.commons.exceptions.DataConversionException;
import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.commons.util.FileUtil;
import seedu.superta.model.ReadOnlySuperTaClient;

/**
 * A class to access SuperTaClient data stored as an xml file on the hard disk.
 */
public class XmlSuperTaClientStorage implements SuperTaClientStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlSuperTaClientStorage.class);

    private Path filePath;

    public XmlSuperTaClientStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the client data from a stream.
     * @throws DataConversionException if stream is not in the correct format.
     */
    public static Optional<ReadOnlySuperTaClient> readClientFromStream(InputStream stream)
            throws DataConversionException {
        XmlSerializableSuperTaClient xmlClient = XmlFileStorage.loadDataFromStream(stream);
        try {
            return Optional.of(xmlClient.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in the input stream.");
            throw new DataConversionException(ive);
        }
    }

    public Path getSuperTaClientFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySuperTaClient> readSuperTaClient() throws DataConversionException, IOException {
        return readSuperTaClient(filePath);
    }

    /**
     * Similar to {@link #readSuperTaClient()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySuperTaClient> readSuperTaClient(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("SuperTaClient file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableSuperTaClient xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSuperTaClient(ReadOnlySuperTaClient superTaClient) throws IOException {
        saveSuperTaClient(superTaClient, filePath);
    }

    /**
     * Similar to {@link #saveSuperTaClient(ReadOnlySuperTaClient)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSuperTaClient(ReadOnlySuperTaClient superTaClient, Path filePath) throws IOException {
        requireNonNull(superTaClient);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableSuperTaClient(superTaClient));
    }

}
