package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.resume.Resume;

/**
 * A class to save resume data to markdown text files on disk.
 */
public class MarkdownResumeStorage implements ResumeStorage {

    private static final Logger logger = LogsCenter.getLogger(MarkdownResumeStorage.class);

    private Path filePath;

    public MarkdownResumeStorage() {

    }

    public MarkdownResumeStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getResumeFilePath() {
        return filePath;
    }

    @Override
    public void saveResume(Resume resume) throws IOException {
        saveResume(resume, filePath);
    }

    /**
     * Similar to {@link #saveResume(Resume)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveResume(Resume resume, Path filePath) throws IOException {
        requireNonNull(resume);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        MdFileStorage.saveDataToFile(filePath, resume);
    }

}
