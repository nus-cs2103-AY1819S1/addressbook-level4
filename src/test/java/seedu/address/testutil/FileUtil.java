package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;

/**
 * A utility class to generate files to be used in import tests.
 */
public class FileUtil {
    public static final Path TYPICAL_FILE = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "typicalTest.txt");
    public static final Path EMPTY_FILE = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "emptyTest.txt");
    public static final Path INVALID_FILE = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "invalidTest.txt.jpg");
    private static final String INVALID_FILE_NAME = "noSuchFile";
    /**
     * Returns an import command string for importing the {@code file}.
     */
    public static String getImportCommand(File file) {
        return ImportCommand.COMMAND_WORD + " " + file.getPath();
    }
    public static File getDummyImportFile() {
        return new File(INVALID_FILE_NAME);
    }
    /**
     * Returns a typical import file.
     * @return typical import txt file.
     */
    public static File getTypicalImportFile() {
        File file = TYPICAL_FILE.toFile();
        assertTrue(file.isFile());
        return file;
    }

    /**
    * Returns an empty valid txt file.
    * @return empty txt file.
    */
    public static File getEmptyImportFile() {
        File file = EMPTY_FILE.toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());
        return file;
    }

    /**
     * Returns an invalid file type.
     * @return an invalid file type.
     */
    public static File getInvalidImportFile() {
        File file = INVALID_FILE.toFile();
        assertTrue(file.exists());
        return file;
    }
}
