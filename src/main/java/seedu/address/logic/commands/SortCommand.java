package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sorts all persons in the address book lexicographically.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all students in the Address Book alphabetically by their name.";
    public static final String MESSAGE_SUCCESS = "Sorted all students";
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
