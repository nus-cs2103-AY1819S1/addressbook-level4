package seedu.address.logic.commands;

import java.nio.file.Path;

import seedu.address.storage.Storage;

/**
 * Export data stored in TheTracker to a certain location.
 */
public abstract class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " --[EXTENSION]: Export user data from Tracker "
            + "and store into a specified location with specified file format. \n "
            + "Parameters: FILE_PATH "
            + "Example: " + COMMAND_WORD + " --xml "
            + "/Users/user/Desktop/backup.xml";

    public static final String MESSAGE_SUCCESS = "Export successfully.";
    public static final String MESSAGE_FAIL_READ_FILE = "Failed to read file path. Please recheck the validity "
            + "of the path format.";
    public static final String MESSAGE_FILE_TYPE_NOT_SUPPORTED_OR_TYPE_NOT_MATCH = "This file type is currently not "
            + "supported; Or the file type you want to convert is not consistent with the extension of the file";
    public static final String MESSAGE_FAIL_XML_TXT_CONVERSION = "Failed to export as a txt file. Please recheck the "
            + "validation of file path.";

    protected Path exportedFilePath;

    public ExportCommand(Path filePath) {
        this.exportedFilePath = filePath;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
