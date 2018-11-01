package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;

import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;

/**
 * Deletes a prescription from health book
 */
public class DeletePrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "delete-prescription";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a prescription to an appointment. "
            + "Parameters: "
            + PREFIX_INDEX + "APPOINTMENT ID "
            + PREFIX_MEDICINE_NAME + "MEDICINE_NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "5 "
            + PREFIX_MEDICINE_NAME + "Paracetamol ";

    public static final String MESSAGE_DELETE_PRESCRIPTION_SUCCESS = "Deleted Prescription: %1$s";
    public static final String MESSAGE_INVALID_DELETE_PRESCRIPTION =
            "This prescription does not exist in the HealthBook";
    public static final String MESSAGE_APPOINTMENT_DOES_NOT_EXIST = "This appointment does not exist";

    private final int id;
    private final MedicineName medicineName;

    public DeletePrescriptionCommand(int id, MedicineName medicineName) {
        this.id = id;
        this.medicineName = medicineName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar) throws CommandException {
        requireNonNull(model);
        List<Appointment> appointmentList = model.getFilteredAppointmentList();

        // check if appointment exists
        Appointment appointmentToEdit = null;
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId() == id) {
                appointmentToEdit = appointment;
                break;
            }
        }

        // appointment does not exist
        if (appointmentToEdit == null) {
            throw new CommandException(String.format(MESSAGE_APPOINTMENT_DOES_NOT_EXIST));
        }

        // check if prescription exists
        Prescription prescriptionToBeDeleted = null;

        for (Prescription prescription : appointmentToEdit.getPrescriptions()) {
            if (prescription.getMedicineName().equals(medicineName)) {
                prescriptionToBeDeleted = prescription;
                break;
            }
        }

        // prescription does not exist in appointment
        if ( prescriptionToBeDeleted == null) {
            throw new CommandException(String.format(MESSAGE_INVALID_DELETE_PRESCRIPTION));
        }

        // deleting appointment
        Appointment editedAppointment = new Appointment(new AppointmentId(appointmentToEdit.getAppointmentId()),
                appointmentToEdit.getDoctor(),
                appointmentToEdit.getPatient(),
                appointmentToEdit.getDateTime(),
                appointmentToEdit.getStatus(),
                appointmentToEdit.getComments(),
                appointmentToEdit.getPrescriptions());
        editedAppointment.deletePrescription(medicineName.toString());

        model.updateAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PRESCRIPTION_SUCCESS, medicineName.toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof DeletePrescriptionCommand)) {
            return false;
        }

        DeletePrescriptionCommand e = (DeletePrescriptionCommand) o;
        return medicineName.equals(e.medicineName);

    }

}
