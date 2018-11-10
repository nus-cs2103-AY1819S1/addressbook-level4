package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Export data to a xml file.
 */
public class ExportXmlCommand extends ExportCommand {

    private String exportedFilePath;
    private FileType fileType;

    public ExportXmlCommand(String filePath, FileType fileType) {
        super(filePath);
        this.exportedFilePath = filePath;
        this.fileType = fileType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        requireNonNull(storage);

        if (!isValidXmlFile()) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE_PATH));
        }

        if (!Files.isWritable(Paths.get(exportedFilePath))) {
            throw new CommandException(String.format(MESSAGE_FILE_PERMISSION_DENIED));
        }

        try {
            storage.saveAddressBook(model.getAddressBook(), Paths.get(exportedFilePath));
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAIL_READ_FILE));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, exportedFilePath));
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    private boolean isValidXmlFile() {
        return isValidFilePath() && exportedFilePath.endsWith(".xml");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportXmlCommand)
                && exportedFilePath.equals(((ExportXmlCommand) other).exportedFilePath);
    }
}
