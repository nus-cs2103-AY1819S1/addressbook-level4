package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AssignmentListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all assignments in the assignment list to the user.
 */
public class ListAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "listassignments";

    public static final String MESSAGE_SUCCESS = "Listed all assignments";

    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        EventsCenter.getInstance().post(new AssignmentListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
