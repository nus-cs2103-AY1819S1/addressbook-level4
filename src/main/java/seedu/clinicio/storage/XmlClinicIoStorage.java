package seedu.clinicio.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.exceptions.DataConversionException;
import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.commons.util.FileUtil;
import seedu.clinicio.model.ReadOnlyClinicIo;

/**
 * A class to access ClinicIo data stored as an xml file on the hard disk.
 */
public class XmlClinicIoStorage implements ClinicIoStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlClinicIoStorage.class);

    private Path filePath;

    public XmlClinicIoStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getClinicIoFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyClinicIo> readClinicIo() throws DataConversionException, IOException {
        return readClinicIo(filePath);
    }

    /**
     * Similar to {@link #readClinicIo()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyClinicIo> readClinicIo(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ClinicIO file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableClinicIo xmlClinicIo = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlClinicIo.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveClinicIo(ReadOnlyClinicIo clinicIo) throws IOException {
        saveClinicIo(clinicIo, filePath);
    }

    /**
     * Similar to {@link #saveClinicIo(ReadOnlyClinicIo)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveClinicIo(ReadOnlyClinicIo clinicIo, Path filePath) throws IOException {
        requireNonNull(clinicIo);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableClinicIo(clinicIo));
    }

}
