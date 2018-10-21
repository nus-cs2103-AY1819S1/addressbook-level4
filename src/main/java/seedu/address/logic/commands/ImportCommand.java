package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.nio.file.Path;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

/**
 * Import MeetingBook XML Files into MeetingBook
 */
public class ImportCommand extends Command{
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "%s have been imported into MeetingBook.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import XML File to Meetingbook."
            + "Parameters: "
            + PREFIX_PATH + "FilePath\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "backup";

    private Path importPath;

    public ImportCommand(Path filepath) {
        importPath = filepath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        // TODO: Parse import file into existing MeetingBook
        return new CommandResult(String.format(MESSAGE_SUCCESS, importPath.toString()));
    }

}
