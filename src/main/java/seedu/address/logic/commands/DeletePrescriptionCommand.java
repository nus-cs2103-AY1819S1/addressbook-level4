package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a prescription from health book
 */
public class DeletePrescriptionCommand extends Command {

    public static final String MESSAGE_DELETE_PRESCRIPTION_SUCCESS = "Deleted Prescriptiom: %1$s";
    public static final String MESSAGE_INVALID_DELETE_PRESCRIPTION =
            "This prescription does not exist in the HealthBook";
    public static final String MESSAGE_APPOINTMENT_DOES_NOT_EXIST = "This appointment does not exist";

    private final int id;
    private final Prescription prescription;

    public DeletePrescriptionCommand(int id, Prescription prescription) {
        this.id = id;
        this.prescription = prescription;
    }

    public int getId() {
        return id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Appointment> appointmentList = model.getFilteredAppointmentList();
        Prescription prescriptionToBeDeleted = null;

        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId() == id) {
                for (Prescription prescription : appointment.getPrescriptions()) {
                }
            }
        }
    }

}
