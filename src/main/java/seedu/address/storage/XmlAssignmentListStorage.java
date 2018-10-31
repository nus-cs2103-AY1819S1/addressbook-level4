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
import seedu.address.model.ReadOnlyAssignmentList;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlAssignmentListStorage implements AssignmentListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAssignmentListStorage.class);

    private Path filePath;

    public XmlAssignmentListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAssignmentListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAssignmentList> readAssignmentList() throws DataConversionException, IOException {
        return readAssignmentList(filePath);
    }

    /**
     * Similar to {@link #readAssignmentList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAssignmentList> readAssignmentList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("AssignmentList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableAssignmentList xmlAssignmentList = XmlFileStorage.loadAssignmentListDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlAssignmentList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAssignmentList(ReadOnlyAssignmentList assignmentList) throws IOException {
        saveAssignmentList(assignmentList, filePath);
    }

    /**
     * Similar to {@link #saveAssignmentList(ReadOnlyAssignmentList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAssignmentList(ReadOnlyAssignmentList assignmentList, Path filePath) throws IOException {
        requireNonNull(assignmentList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveAssignmentListDataToFile(filePath, new XmlSerializableAssignmentList(assignmentList));
    }
}
