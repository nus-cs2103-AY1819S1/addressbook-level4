package seedu.clinicio.logic.commands;

import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.appointment.Appointment;

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
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
