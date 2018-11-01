package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSUMPTION_PER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.calendar.GoogleCalendar;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * Adds a prescription to an appointment
 */

public class AddPrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "add-prescription";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a prescription to an appointment. "
            + "Parameters: "
            + PREFIX_INDEX + "APPOINTMENT_ID "
            + PREFIX_MEDICINE_NAME + "MEDICINE_NAME "
            + PREFIX_DOSAGE + "DOSAGE "
            + PREFIX_CONSUMPTION_PER_DAY + "CONSUMPTION_PER_DAY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "563 "
            + PREFIX_MEDICINE_NAME + "Paracetamol "
            + PREFIX_DOSAGE + "2 "
            + PREFIX_CONSUMPTION_PER_DAY + "3 ";

    public static final String MESSAGE_SUCCESS = "New Prescription added: %1$s";
    public static final String MESSAGE_DUPLICATE_PRESCRIPTION = "This prescription already exists in the appointment";

    private final Prescription toAdd;

    /**
     * Creates an AddPrescriptionCommand to add the specified {@code Person}
     */
    public AddPrescriptionCommand(Prescription prescription) {
        requireAllNonNull(prescription);
        toAdd = prescription;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Appointment> appointmentList = model.getFilteredAppointmentList();
        Appointment appointmentToEdit = appointmentList.stream()
                .filter(appt -> appt.getAppointmentId() == toAdd.getId())
                .findFirst()
                .orElse(null);

        if (appointmentToEdit.getPrescriptions().contains(toAdd)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE_PRESCRIPTION));
        }
        Appointment editedAppointment = new Appointment(new AppointmentId(appointmentToEdit.getAppointmentId()),
                appointmentToEdit.getDoctor(),
                appointmentToEdit.getPatient(),
                appointmentToEdit.getDateTime(),
                appointmentToEdit.getStatus(),
                appointmentToEdit.getComments(),
                appointmentToEdit.getPrescriptions());
        editedAppointment.addPrescription(toAdd);

        model.updateAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.commitAddressBook();

        List<Person> personList = model.getFilteredPersonList();
        Patient patient = (Patient) personList.stream()
                .filter(person -> person.getName().toString().equals(editedAppointment.getPatient()))
                .findFirst()
                .orElse(null);

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(patient));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getMedicineName()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof AddPrescriptionCommand)) {
            return false;
        }

        AddPrescriptionCommand e = (AddPrescriptionCommand) o;
        return toAdd.equals(e.toAdd);

    }

}
