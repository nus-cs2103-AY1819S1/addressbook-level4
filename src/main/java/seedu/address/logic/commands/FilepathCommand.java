package seedu.address.logic.commands;

import java.nio.file.Path;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

/**
 * Change the filepath of addressbook of where addressbook will be saved.
 */
public class FilepathCommand extends Command {
    public static final String COMMAND_WORD = "filepath";

    public static final String MESSAGE_CHANGEPATH_SUCCESS = "Addressbook now will be saved at: %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change current saving location of addressbook "
            + "to the new specific location.\n"
            + "Parameters: "
            + PREFIX_PATH + "FilePath\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "newHome.xml";

    private Path filepath;

    public FilepathCommand(Path filePath) {
        this.filepath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.changeUserPrefs(filepath);
        return new CommandResult(String.format(MESSAGE_CHANGEPATH_SUCCESS, filepath.toString()));
    }

}
