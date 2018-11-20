package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;

/**
 * Deletes a lesson from the address book.
 */
public class DeleteAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "deleteAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the attendance identified by the index number used in the displayed attendance list.\n"
            + "Parameters: "
            + PREFIX_ID + "SESSION_INDEX\n"
            + "Example: " + COMMAND_WORD + " id/1";

    public static final String MESSAGE_DELETE_ATTENDANCE_SUCCESS = "Deleted Session: %1$s";

    private final Index targetIndex;

    public DeleteAttendanceCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Attendance> lastShownList = model.getFilteredAttendanceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Attendance attendanceToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAttendance(attendanceToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ATTENDANCE_SUCCESS, attendanceToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAttendanceCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteAttendanceCommand) other).targetIndex)); // state check
    }
}
