package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sorts all assignments in the address book based on deadline.
 */
public class SortAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "sortAssignment";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all assignments in the Address Book by their deadline.";
    public static final String MESSAGE_SUCCESS = "Sorted all assignments";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.sortAssignment();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
