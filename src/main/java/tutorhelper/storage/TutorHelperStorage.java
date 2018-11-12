package tutorhelper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tutorhelper.commons.exceptions.DataConversionException;
import tutorhelper.model.ReadOnlyTutorHelper;
import tutorhelper.model.TutorHelper;

/**
 * Represents a storage for {@link TutorHelper}.
 */
public interface TutorHelperStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTutorHelperFilePath();

    /**
     * Returns TutorHelper data as a {@link ReadOnlyTutorHelper}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTutorHelper> readTutorHelper() throws DataConversionException, IOException;

    /**
     * @see #getTutorHelperFilePath()
     */
    Optional<ReadOnlyTutorHelper> readTutorHelper(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTutorHelper} to the storage.
     * @param tutorHelper cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTutorHelper(ReadOnlyTutorHelper tutorHelper) throws IOException;

    void saveTutorHelper(ReadOnlyTutorHelper tutorHelper, Path filePath) throws IOException;

}
