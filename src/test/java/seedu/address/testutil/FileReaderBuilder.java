package seedu.address.testutil;

import seedu.address.model.filereader.FilePath;
import seedu.address.model.filereader.FileReader;

import java.nio.file.Paths;

/**
 * A utility class to help with building FileReader objects.
 */

public class FileReaderBuilder {
    public static final String DEFAULT_CSV_FILE_PATH = Paths
            .get("src", "test", "data", "ImportContactsTest")
            .resolve("ImportContacts.csv")
            .toFile()
            .getAbsolutePath();

    public static final String EMPTY_CSV_FILE_PATH = Paths
            .get("src", "test", "data", "ImportContactsTest")
            .resolve("EmptyImportContacts.csv")
            .toFile()
            .getAbsolutePath();


    private FileReader FileReader;

    public FileReaderBuilder() {
        FilePath filePath = new FilePath(DEFAULT_CSV_FILE_PATH);
        FileReader = new FileReader(filePath);
    }

    /**
     * Sets the {@code FileReader} of the {@code FileReader} that we are building.
     */
    public FileReaderBuilder empty() {
        FilePath filePath = new FilePath(EMPTY_CSV_FILE_PATH);
        this.FileReader = new FileReader(filePath);
        return this;
    }

    public FileReader build() { return FileReader; }
}
