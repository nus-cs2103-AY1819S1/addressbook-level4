package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Change the filepath of addressbook of where addressbook will be saved.
 */
public class FilepathCommand extends Command {
    public static final String COMMAND_WORD = "filepath";
    public static final String COMMAND_SHOW = "show";

    public static final String MESSAGE_CHANGEPATH_SUCCESS = "Addressbook now will be saved at: %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change current saving location of addressbook "
            + "to the new specific location. "
            + "Parameters: "
            + PREFIX_PATH + "FilePath or " + COMMAND_SHOW + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "newHome.xml\n"
            + "Example: " + COMMAND_WORD + " "
            + COMMAND_SHOW + " : Show the current storage path.";
    public static final String MESSAGE_SHOWPATH_SUCCESS = "Addressbook is stored at %s";
    private Path filepath;

    public FilepathCommand(Path filePath) {
        this.filepath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (filepath == null) {
            Path currentPath = model.getAddressBookFilePath();
            return new CommandResult(String.format(MESSAGE_SHOWPATH_SUCCESS, currentPath.toString()));
        }

        model.changeUserPrefs(filepath);
        return new CommandResult(String.format(MESSAGE_CHANGEPATH_SUCCESS, filepath.toString()));
    }

}
