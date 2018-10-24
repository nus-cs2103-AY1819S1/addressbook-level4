package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;


/**
 * Adds a patient's appointment to the health book.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "delete-appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Deletes a patient's appointment in the health book. "
            + "Parameters: "
            + PREFIX_INDEX + "APPOINTMENT_INDEX \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "3 ";

    public static final String MESSAGE_SUCCESS = "Deleted Appointment: %1$s";
    public static final String MESSAGE_INVALID_APPOINTMENT_INDEX = "Appointment index is invalid";

    private final int appointmentId;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public DeleteAppointmentCommand(int appointmentId) {
        requireAllNonNull(appointmentId);
        this.appointmentId = appointmentId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();
        Appointment appointment = lastShownList.stream()
                .filter(appt -> appt.getAppointmentId() == appointmentId)
                .findFirst()
                .orElse(null);

        if (appointment == null) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_INDEX);
        }

        model.deleteAppointment(appointment);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && (appointmentId == ((DeleteAppointmentCommand) other).appointmentId));
    }
}
