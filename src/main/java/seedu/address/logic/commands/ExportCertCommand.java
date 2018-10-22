package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports a PDF document with data on a volunteer's involvement with the organisation.
 */
public class ExportCertCommand extends Command {

    public static final String COMMAND_WORD = "exportcert";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports a PDF certificate for the volunteer at "
            + "the specified index in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "exportcert command has not been implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
