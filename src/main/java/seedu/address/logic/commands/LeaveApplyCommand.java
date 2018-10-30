package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.person.Person;

/**
 * Apply for leave.
 */
public class LeaveApplyCommand extends Command {

    public static final String COMMAND_WORD = "leaveapply";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Apply for leave. "
            + "Parameters: "
            + PREFIX_LEAVE_DESCRIPTION + " DESCRIPTION "
            + "[" + PREFIX_LEAVE_DATE + " YYYY-MM-DD]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LEAVE_DESCRIPTION + " John's family holiday "
            + PREFIX_LEAVE_DATE + " 2018-12-10 "
            + PREFIX_LEAVE_DATE + " 2018-12-11 "
            + PREFIX_LEAVE_DATE + " 2018-12-12 ";

    public static final String MESSAGE_SUCCESS = "New leave application created: %1$s";

    private final LeaveApplication leaveApplicationToAdd;

    /**
     * Creates an LeaveApplyCommand to add the specified {@code LeaveApplication}
     */
    public LeaveApplyCommand(LeaveApplication leaveApplication) {
        requireAllNonNull(leaveApplication);
        leaveApplicationToAdd = leaveApplication;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.addLeaveApplication(leaveApplicationToAdd, model.getLoggedInUser().getPerson());
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, leaveApplicationToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveApplyCommand // instanceof handles nulls
                && leaveApplicationToAdd.equals(((LeaveApplyCommand) other).leaveApplicationToAdd));
    }
}
