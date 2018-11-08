package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEAVEAPPLICATIONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LeaveListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.User;

/**
 * Lists all leave applications that employees have made to the user.
 */
public class LeaveListCommand extends Command {

    public static final String COMMAND_WORD = "leavelist";

    public static final String MESSAGE_SUCCESS = "Listed all leave applications";

    public LeaveListCommand() {
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        requireNonNull(model);

        User loggedInUser = model.getLoggedInUser();
        if (loggedInUser.equals(User.getAdminUser())
                || loggedInUser.getPerson().getPermissionSet()
                .getGrantedPermission().contains(Permission.VIEW_EMPLOYEE_LEAVE)
                || loggedInUser.getPerson().getPermissionSet()
                .getGrantedPermission().contains(Permission.APPROVE_LEAVE)) {
            model.updateFilteredLeaveApplicationList(PREDICATE_SHOW_ALL_LEAVEAPPLICATIONS);
        } else {
            model.updateFilteredLeaveApplicationListForPerson(loggedInUser.getPerson());
        }

        EventsCenter.getInstance().post(new LeaveListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
