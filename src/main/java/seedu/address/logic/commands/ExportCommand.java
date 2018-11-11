package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author kengwoon

/**
 * Exports an XML file of current contacts in Hallper.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports an XML file of current contacts in Hallper.\n "
        + "Parameters: "
        + "export "
        + "dst/C://Users/Documents "
        + "fn/FILENAME.xml";

    public static final String MESSAGE_SUCCESS = "Contacts successfully exported to %1$s.";
    public static final String MESSAGE_NO_WRITE_PERMISSION = "Permission to write to %1$s denied. Please enter a "
            + "different destination path.";

    private final Path path;

    /**
     * Creates an ExportCommand to export the specified file.
     */
    public ExportCommand(Path path) {
        requireNonNull(path);
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            requireNonNull(model);
            model.exportAddressBook(path);
            FileUtil.createIfMissing(path);
            return new CommandResult(String.format(MESSAGE_SUCCESS, path));
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_NO_WRITE_PERMISSION, path.toString()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportCommand // instanceof handles nulls
            && path.equals(((ExportCommand) other).path));
    }

}
