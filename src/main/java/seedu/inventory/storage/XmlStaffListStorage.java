package seedu.inventory.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.model.ReadOnlyStaffList;

/**
 * A class to access StaffStorage data stored as a xml file on the hard disk.
 */
public class XmlStaffListStorage implements StaffStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlStaffListStorage.class);

    private Path filePath;

    /**
     * Constructs XmlStaffStorage for reading and writing data from the xml file.
     * @param filePath where the data is stored at.
     */
    public XmlStaffListStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the storage.
     *
     * @return the file path of the storage for staff list
     */
    public Path getStaffListFilePath() {
        return filePath;
    }

    /**
     * Returns data which is stored in the xml file.
     *
     * @return a list of staff for read only
     * @throws DataConversionException if errors occurred while converting data
     * @throws IOException if errors occur while reading file
     */
    @Override
    public Optional<ReadOnlyStaffList> readStaffList() throws DataConversionException, IOException {
        return readStaffList(filePath);
    }

    /**
     * Returns the data which is stored in the given xml file path.
     * @param filePath the file path of where the data stored.
     *
     * @return
     * @throws DataConversionException if errors occurred while converting data
     * @throws FileNotFoundException if the file path provided is not found
     */
    @Override
    public Optional<ReadOnlyStaffList> readStaffList(Path filePath)
            throws DataConversionException, FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Staff file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableStaffList xmlStaffList = XmlFileStorage.loadStaffsDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlStaffList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Saves the given {@code ReadOnlyStaffList} into the xml file.
     *
     * @param staffList cannot be null.
     * @throws IOException if errors occurred while saving the data
     */
    @Override
    public void saveStaffList(ReadOnlyStaffList staffList) throws IOException {
        saveStaffList(staffList, filePath);
    }

    /**
     * Saves the given {@code ReadOnlyStaffList} into the xml file with provided {@code Path}.
     *
     * @param staffList cannot be null.
     * @param filePath the file path where staff list will be stored.
     *
     * @throws IOException if errors occurred while saving the data
     */
    @Override
    public void saveStaffList(ReadOnlyStaffList staffList, Path filePath) throws IOException {
        requireNonNull(staffList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveStaffsDataToFile(filePath, new XmlSerializableStaffList(staffList));
    }
}
