package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.model.resume.Resume;

/**
 * Represents a storage for {@link seedu.address.model.resume.Resume}.
 */
public interface ResumeStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getResumeFilePath();

    /**
     * Saves the given {@link Resume} to the storage.
     * @param resume cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveResume(Resume resume) throws IOException;

    /**
     * @see #saveResume(Resume)
     */
    void saveResume(Resume resume, Path filePath) throws IOException;

}
