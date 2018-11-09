package seedu.address.logic.commands;

import java.nio.file.Path;

import seedu.address.storage.Storage;

/**
 * Export data stored in TheTracker to a certain location.
 */
public abstract class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " --[FILE_EXTENSION]: Export user data from TheTracker "
            + "and store it into a specified location with specified file type. \n"
            + "Parameters: [--[FILE_EXTENSION]] [FILE_PATH] \n"
            + "Example: " + COMMAND_WORD + " --xml "
            + "/Users/Anna/Desktop/backup.xml (macOS, Linux) \n"
            + COMMAND_WORD + " --txt C:\\Users\\Anna\\desktop\\info.txt (Windows)";

    public static final String MESSAGE_SUCCESS = "Export successfully to file path: %1$s";
    public static final String MESSAGE_FAIL_READ_FILE = "Failed to read file path. Please recheck the validity "
            + "of the file path: %1$s.";
    public static final String MESSAGE_FILE_TYPE_NOT_SUPPORTED_OR_TYPE_NOT_MATCH = "Failed to export user data. Please recheck: \n"
            + "1. The validity of the file extension. \n"
            + "2. The consistency of parameter [--[EXTENSION]] and the file extension.";
    public static final String MESSAGE_FAIL_XML_TXT_CONVERSION = "Failed to export the user data as a txt file. Please recheck the "
            + "validation of file path: %1$s.";

    protected Path exportedFilePath;

    public ExportCommand(Path filePath) {
        this.exportedFilePath = filePath;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
