package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * List all tasks with the current date.
 */
public class ListDayCommand extends Command {
    public static final String COMMAND_WORD = "listday";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all task with the current date.\n";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "list today command not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
