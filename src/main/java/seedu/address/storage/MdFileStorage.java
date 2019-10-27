package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.util.MdUtil;
import seedu.address.model.resume.Resume;

/**
 * Stores resume data in a Markdown file
 */
public class MdFileStorage {
    /**
     * Empty constructor to prevent instantiation.
     */
    private MdFileStorage() {}

    /**
     * Saves the given resume data to the specified file.
     */
    public static void saveDataToFile(Path file, Resume resume)
            throws IOException {
        MdUtil.saveMdFile(resume, file);
    }
}
