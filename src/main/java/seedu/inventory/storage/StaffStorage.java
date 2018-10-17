package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.staff.Staff;

/**
 * @author darren96
 * Represents a storage for {@link Staff}
 */
public interface StaffStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStaffListFilePath();

    /**
     * Returns Staff data as a {@link ReadOnlyStaffList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there is any problem when reading from the storage.
     */
    Optional<ReadOnlyStaffList> readStaffList() throws DataConversionException, IOException;

    /**
     * Returns Staff data as a {@link ReadOnlyStaffList} from the given file path.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @param filePath the file path of where the data stored.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there is any problem when reading from the storage.
     */
    Optional<ReadOnlyStaffList> readStaffList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStaffList} to the storage.
     * @param staffList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStaffList(ReadOnlyStaffList staffList) throws IOException;

    /**
     * Saves the given {@link ReadOnlyStaffList} to the storage which is with the given file path.
     *
     * @param staffList cannot be null.
     * @param filePath the file path where staff list will be stored.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStaffList(ReadOnlyStaffList staffList, Path filePath) throws IOException;
}
