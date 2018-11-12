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
import seedu.address.model.ReadOnlyArchiveList;

/**
 * A class to access ArchiveList data stored as an xml file on the hard disk.
 */
public class XmlArchiveListStorage implements ArchiveListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlArchiveListStorage.class);

    private Path filePath;

    public XmlArchiveListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getArchiveListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyArchiveList> readArchiveList() throws DataConversionException, IOException {
        return readArchiveList(filePath);
    }

    /**
     * Similar to {@link #readArchiveList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyArchiveList> readArchiveList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ArchiveList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableArchiveList xmlArchiveList = XmlFileStorage.loadArchiveDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlArchiveList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveArchiveList(ReadOnlyArchiveList archiveList) throws IOException {
        saveArchiveList(archiveList, filePath);
    }

    /**
     * Similar to {@link #saveArchiveList(ReadOnlyArchiveList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveArchiveList(ReadOnlyArchiveList archiveList, Path filePath) throws IOException {
        requireNonNull(archiveList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveArchiveListDataToFile(filePath, new XmlSerializableArchiveList(archiveList));
    }
}
