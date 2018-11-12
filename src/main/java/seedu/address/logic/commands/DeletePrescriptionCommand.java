package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * Deletes a prescription from health book
 */
public class DeletePrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "delete-prescription";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a prescription to an appointment. \n"
            + "Parameters: "
            + "APPOINTMENT ID "
            + PREFIX_MEDICINE_NAME + "MEDICINE_NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + "10005 "
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
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
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
        if (prescriptionToBeDeleted == null) {
            throw new CommandException(String.format(MESSAGE_INVALID_DELETE_PRESCRIPTION));
        }

        // deleting prescription
        ArrayList<Prescription> allPrescriptions = new ArrayList<Prescription>();
        allPrescriptions.addAll(appointmentToEdit.getPrescriptions());

        Appointment editedAppointment = new Appointment(new AppointmentId(appointmentToEdit.getAppointmentId()),
                appointmentToEdit.getDoctor(),
                appointmentToEdit.getPatient(),
                appointmentToEdit.getDateTime(),
                appointmentToEdit.getStatus(),
                appointmentToEdit.getComments(),
                allPrescriptions);
        editedAppointment.deletePrescription(medicineName.toString());
        model.setAppointment(appointmentToEdit, editedAppointment);

        //editing persons
        List<Person> personList = model.getFilteredPersonList();
        Doctor doctorToEdit = null;
        Patient patientToEdit = null;

        for (Person person : personList) {
            if (person instanceof Doctor) {
                if (appointmentToEdit.getDoctor().equals(person.getName().toString())) {
                    if (((Doctor) person).hasAppointment(id)) {
                        doctorToEdit = (Doctor) person;
                    }
                }
            }
            if (person instanceof Patient) {
                if (appointmentToEdit.getPatient().equals(person.getName().toString())) {
                    if (((Patient) person).hasAppointment(id)) {
                        patientToEdit = (Patient) person;
                    }
                }
            }
            if (doctorToEdit != null && patientToEdit != null) {
                break;
            }
        }

        // Doctor only stores upcoming appts while patients store both upcoming and past appt
        if (appointmentToEdit.getStatus().equals("UPCOMING")) {
            if (patientToEdit == null || doctorToEdit == null) {
                throw new CommandException(MESSAGE_APPOINTMENT_DOES_NOT_EXIST);
            }
        } else {
            if (patientToEdit == null) {
                throw new CommandException(MESSAGE_APPOINTMENT_DOES_NOT_EXIST);
            }
        }

        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(),
                patientToEdit.getEmail(), patientToEdit.getAddress(), patientToEdit.getRemark(),
                patientToEdit.getTags(), patientToEdit.getTelegramId(), patientToEdit.getUpcomingAppointments(),
                patientToEdit.getPastAppointments(), patientToEdit.getMedicalHistory());
        editedPatient.setAppointment(appointmentToEdit, editedAppointment);
        model.updatePerson(patientToEdit, editedPatient);

        if (doctorToEdit != null) {
            Doctor editedDoctor = new Doctor(doctorToEdit.getName(), doctorToEdit.getPhone(), doctorToEdit.getEmail(),
                    doctorToEdit.getAddress(), doctorToEdit.getRemark(), doctorToEdit.getTags(),
                    doctorToEdit.getUpcomingAppointments());
            editedDoctor.setAppointment(appointmentToEdit, editedAppointment);
            model.updatePerson(doctorToEdit, editedDoctor);
        }

        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(editedPatient));
        return new CommandResult(String.format(MESSAGE_DELETE_PRESCRIPTION_SUCCESS,
                prescriptionToBeDeleted.getMedicineName()));
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
