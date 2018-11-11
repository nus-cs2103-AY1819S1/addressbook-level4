package seedu.address.logic.commands;

import seedu.address.storage.Storage;

/**
 * Export data stored in TheTracker to a certain location.
 */
public abstract class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " --[FILE_EXTENSION]: Export user data from TheTracker "
            + "and store it into a specified location with specified file type. \n"
            + "Parameters: [--[FILE_EXTENSION]] [FILE_PATH] \n"
            + "Example: \n " + COMMAND_WORD + " --xml "
            + "/Users/Anna/Desktop/exported.xml (macOS, Linux) \n"
            + COMMAND_WORD + " --txt C:\\Users\\Anna\\desktop\\exported.txt (Windows)";

    public static final String MESSAGE_SUCCESS = "Export successfully to file path: %1$s";
    public static final String MESSAGE_FAIL_READ_FILE = "Failed to read file path. Please recheck the validity "
            + "of the file path and the permission to the file.";
    public static final String MESSAGE_FILE_TYPE_NOT_SUPPORTED = "The file type you want to export as is "
            + "currently not supported; or the parameter format is wrong. \n"
            + "Current supported file type: XML and TXT \n"
            + "Parameter: \"--xml\" or \"--txt\"";
    public static final String MESSAGE_FAIL_XML_TXT_CONVERSION = "Failed to export the user data as a txt file. ";
    public static final String MESSAGE_INVALID_FILE_PATH =
            "File name can only contain alphanumeric and underscore. Please recheck.";
    public static final String MESSAGE_FILE_PERMISSION_DENIED = "Permission to write to file is denied.";
    public static final String WIN_FILE_PATH_REGEX =
            "^(?:[a-zA-Z]\\:|\\\\[\\w\\.]+\\\\[\\w.$]+)*\\\\(?:[\\w]+\\\\)*\\w([\\w.])+$";
    public static final String MACOS_LINUX_FILE_PATH_REGEX =
            "^(\\/)*([\\w\\.]+\\/[\\w.$]+\\/)*(?:[\\w]+\\/)*\\w([\\w.])+$";

    protected String exportedFilePath;

    public ExportCommand(String filePath) {
        this.exportedFilePath = filePath;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Specifies the type of the exported file specified
     */
    public enum FileType {
        XML,
        TXT
    }

    protected boolean isValidFilePath() {
        return exportedFilePath.matches(WIN_FILE_PATH_REGEX)
                || exportedFilePath.matches(MACOS_LINUX_FILE_PATH_REGEX);
    }
}
