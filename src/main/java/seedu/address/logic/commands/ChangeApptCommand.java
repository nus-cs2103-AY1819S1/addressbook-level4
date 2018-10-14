package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.appointment.Appointment;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

/**
 * Changes the appointment date in schedule.
 * Assumes that one person can make one appointment
 */
public class ChangeApptCommand extends Command {

    public static final String COMMAND_WORD = "changeappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reschedules appointment of the appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "3 4 2018 "
            + PREFIX_TIME + "12 30";

    public static final String MESSAGE_SUCCESS = "Appointment changed: %1$s";
    public static final String MESSAGE_CLASH_APPOINTMENT = "Appointment clashes with another slot.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment is already scheduled.";

    public final Index targetIndex;
    //public final AppointmentRescheduler appointmentRescheduler;

    public ChangeApptCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) throws CommandException {
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
