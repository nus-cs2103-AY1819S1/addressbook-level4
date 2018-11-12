package seedu.address.testutil;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.model.portation.ImportFile;

/**
 * A utility class to generate files to be used in import tests.
 */
public class ImportFileUtil {
    public static final Path TYPICAL_FILE = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "typicalImportFile.txt");
    public static final Path VALID_FILE_NO_TOPIC = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "validImportFileNoTopic.txt");
    public static final Path VALID_FILE_WITH_TOPIC = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "validImportFileWithTopic.txt");
    public static final Path VALID_FILE_NO_CARDS = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "validImportFileNoCards");
    public static final Path VALID_FILE_DUPLICATE_CARDS = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "validImportFileDuplicateCards");

    public static final Path INVALID_EMPTY_FILE = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "invalidImportFileEmpty.txt");
    public static final Path INVALID_FILE_TYPE = Paths.get("src", "test", "data", "TxtFileUtilTest",
            "invalidTest.txt.jpg");
    public static final Path INVALID_NOT_A_FILE = Paths.get("src", "test", "data",
            "invalidImportFileNotFile");
    public static final Path INVALID_FILE_INVALID_FORMAT = Paths.get("src", "test", "data",
            "invalidImportFileInvalidFormat");;

    private static final String INVALID_FILE_NAME = "noSuchFile";

    /**
     * Returns an import command string for importing the {@code filePath}.
     */
    public static String getImportCommand(String filePath) {
        return ImportCommand.COMMAND_WORD + " " + filePath;
    }

    /**
     * Returns a typical file to be imported.
     */
    public static ImportFile getTypicalImportFile() {
        return new ImportFile(TYPICAL_FILE.toString());
    }

    /**
     * Returns a valid file to be imported with one question and answer pair without a topic.
     * Question = "question". Answer = "answer".
     */
    public static ImportFile getValidNoTopicImportFile() {
        return new ImportFile(VALID_FILE_NO_TOPIC.toString());
    }
    /**
     * Returns a valid file to be imported with one question and answer pair with a topic.
     * Question = "question". Answer = "answer". Topic = "topic".
     */
    public static ImportFile getValidWithTopicImportFile() {
        return new ImportFile(VALID_FILE_WITH_TOPIC.toString());
    }

    /**
     * Returns a pseudo ImportFile that does not actually exist.
     */
    public static ImportFile getDummyImportFile() {
        return new ImportFile(INVALID_FILE_NAME);
    }

    /**
    * Returns a valid empty txt ImportFile.
    */
    public static ImportFile getEmptyImportFile() {
        return new ImportFile(INVALID_EMPTY_FILE.toString());
    }

    /**
     * Returns an invalid file type ImportFile.
     */
    public static ImportFile getInvalidImportFileType() {
        return new ImportFile(INVALID_FILE_TYPE.toString());
    }

    /**
     * Returns an invalid ImportFile that is a directory.
     */
    public static ImportFile getInvalidImportFileNotFile() {
        return new ImportFile(INVALID_NOT_A_FILE.toString());
    }

    /**
     * Returns an invalid ImportFile with an invalid format.
     */
    public static ImportFile getInvalidImportFileInvalidFormat() {
        return new ImportFile(INVALID_FILE_INVALID_FORMAT.toString());
    }

    /**
     * Returns a valid ImportFile with no questions and answer but contains a valid topic.
     */
    public static ImportFile getValidImportFileNoCards() {
        return new ImportFile(VALID_FILE_NO_CARDS.toString());
    }

    /**
     * Returns a valid ImportFile that contains duplicate questions.
     */
    public static ImportFile getValidImportFileDuplicateCards() {
        return new ImportFile(VALID_FILE_DUPLICATE_CARDS.toString());
    }
}
