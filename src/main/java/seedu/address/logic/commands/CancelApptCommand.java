package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

//@@author gingivitiss

/**
 * Cancels appointments from the schedule.
 */
public class CancelApptCommand extends Command {

    public static final String COMMAND_WORD = "cancelappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels appointment from the schedule. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CANCEL_APPOINTMENT_SUCCESS = "Appointment cancelled: %1$s";

    private final Index targetIndex;

    public CancelApptCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Appointment targetAppointment = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAppointment(targetAppointment);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_CANCEL_APPOINTMENT_SUCCESS, targetAppointment));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof CancelApptCommand) {
            return false;
        }
        return targetIndex.equals(((CancelApptCommand) other).targetIndex);
    }
}
