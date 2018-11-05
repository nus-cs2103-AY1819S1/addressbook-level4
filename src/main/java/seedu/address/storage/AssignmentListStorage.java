package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAssignmentList;

/**
 * Represents a storage for {@link seedu.address.model.AssignmentList}.
 */
public interface AssignmentListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAssignmentListFilePath();

    /**
     * Returns AssignmentList data as a {@link ReadOnlyAssignmentList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAssignmentList> readAssignmentList() throws DataConversionException, IOException;

    /**
     * @see #getAssignmentListFilePath()
     */
    Optional<ReadOnlyAssignmentList> readAssignmentList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAssignmentList} to the storage.
     * @param assignmentList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAssignmentList(ReadOnlyAssignmentList assignmentList) throws IOException;

    /**
     * @see #saveAssignmentList(ReadOnlyAssignmentList)
     */
    void saveAssignmentList(ReadOnlyAssignmentList assignmentList, Path filePath) throws IOException;
}
