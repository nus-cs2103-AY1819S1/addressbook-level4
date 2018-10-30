package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leaveapplication.LeaveApplication;

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

    private final LeaveApplication toAdd;

    /**
     * Creates an LeaveApplyCommand to add the specified {@code LeaveApplication}
     */
    public LeaveApplyCommand(LeaveApplication leaveApplication) {
        requireNonNull(leaveApplication);
        toAdd = leaveApplication;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // model.addLeaveApplication(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveApplyCommand // instanceof handles nulls
                && toAdd.equals(((LeaveApplyCommand) other).toAdd));
    }
}
