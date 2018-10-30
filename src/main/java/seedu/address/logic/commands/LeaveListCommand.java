package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all leave applications that employees have made to the user.
 */
public class LeaveListCommand extends Command {

    public static final String COMMAND_WORD = "leavelist";

    public static final String MESSAGE_SUCCESS = "Listed all leave applications";


    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
