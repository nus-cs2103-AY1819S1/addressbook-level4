package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sort the list of deck in department's lexicographical order.
 */
public class SortDeptCommand extends Command {

    public static final String COMMAND_WORD = "sortDept";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by department";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortByDept();
        model.commitSsenisub();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
