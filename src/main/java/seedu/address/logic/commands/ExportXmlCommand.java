package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Export data to a xml file.
 */
public class ExportXmlCommand extends ExportCommand {

    protected Path exportedFilePath;

    public ExportXmlCommand(Path filePath) {
        super(filePath);
        this.exportedFilePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        requireNonNull(storage);

        try {
            storage.saveAddressBook(model.getAddressBook(), exportedFilePath);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAIL_READ_FILE, exportedFilePath));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, exportedFilePath));
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportXmlCommand)
                && exportedFilePath.equals(((ExportXmlCommand) other).exportedFilePath);
    }
}
