package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_PHONE;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.InvalidInputOutputException;
import seedu.address.model.appointment.exceptions.InvalidSecurityAccessException;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Adds a patient's appointment to the health book.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "add-appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient's appointment to the health book.\n"
            + "Parameters: "
            + PREFIX_PATIENT_NAME + "PATIENT_NAME "
            + "[" + PREFIX_PATIENT_PHONE + "PATIENT_PHONE] "
            + PREFIX_DOCTOR_NAME + "DOCTOR_NAME "
            + "[" + PREFIX_DOCTOR_PHONE + "DOCTOR_PHONE] "
            + PREFIX_DATE_TIME + "DATE_TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_NAME + "John Doe "
            + PREFIX_PATIENT_PHONE + "98765432 "
            + PREFIX_DOCTOR_NAME + "Mary Jane "
            + PREFIX_DOCTOR_PHONE + "98765434 "
            + PREFIX_DATE_TIME + "2018-10-17 15:00 ";

    public static final String MESSAGE_SUCCESS = "New appointment added";
    public static final String MESSAGE_INVALID_PATIENT = "This patient does not exist in the HealthBook";
    public static final String MESSAGE_INVALID_DOCTOR = "This doctor does not exist in the HealthBook";
    public static final String MESSAGE_DOCTOR_CLASH_APPOINTMENT =
            "This doctor already have an appointment in that time slot.";
    public static final String MESSAGE_PATIENT_CLASH_APPOINTMENT =
            "This patient already have an appointment in that time slot.";
    public static final String MESSAGE_DUPLICATE_DOCTOR =
            "There is multiple doctors with this name. Please enter doctor's number to identify the unique doctor";
    public static final String MESSAGE_DUPLICATE_PATIENT =
            "There is multiple patients with this name. Please enter patients's number to identify the unique doctor";
    // TODO - add messages for various cases (e.g. conflict in schedule) here when google calendar is up

    private final Name patientName;
    private final Name doctorName;
    private final LocalDateTime dateTime;
    private final Phone patientPhoneNumber;
    private final Phone doctorPhoneNumber;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Name patientName, Phone patientPhoneNumber,
                                 Name doctorName, Phone doctorPhoneNumber,
                                 LocalDateTime dateTime) {
        requireAllNonNull(patientName, doctorName, dateTime);
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.dateTime = dateTime;
        this.patientPhoneNumber = patientPhoneNumber;
        this.doctorPhoneNumber = doctorPhoneNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Patient patient = null;
        Doctor doctor = null;

        for (Person person : lastShownList) {
            if (person.getName().equals(patientName) && person instanceof Patient) {
                if (patientPhoneNumber != null) {
                    if (person.getPhone().equals(patientPhoneNumber)) {
                        patient = (Patient) person; // Name and Phone number unique
                    }
                } else {
                    if ((patient != null && patient.getName().equals(patientName))
                            || (doctor != null && doctor.getName().equals(patientName))) {
                        throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
                    } else {
                        patient = (Patient) person;
                    }
                }
            }
            if (person.getName().equals(doctorName) && person instanceof Doctor) {
                if (doctorPhoneNumber != null) {
                    if (person.getPhone().equals(doctorPhoneNumber)) {
                        doctor = (Doctor) person; // Name and Phone number unique
                    }
                } else {
                    if ((patient != null && patient.getName().equals(doctorName))
                            || (doctor != null && doctor.getName().equals(doctorName))) {
                        throw new CommandException(MESSAGE_DUPLICATE_DOCTOR);
                    } else {
                        doctor = (Doctor) person;
                    }
                }
            }
        }

        if (patient == null) {
            throw new CommandException(MESSAGE_INVALID_PATIENT);
        }
        if (doctor == null) {
            throw new CommandException(MESSAGE_INVALID_DOCTOR);
        }

        Appointment appointment = new Appointment(model.getAppointmentCounter(), doctor.getName().toString(),
                patient.getName().toString(), dateTime);

        try {
            googleCalendar.addAppointment(doctor.getName().toString() + doctor.getPhone().toString(),
                    appointment);
        } catch (GeneralSecurityException e) {
            throw new InvalidSecurityAccessException();
        } catch (IOException e) {
            throw new InvalidInputOutputException();
        }

        if (doctor.hasClashForAppointment(appointment)) {
            throw new CommandException(MESSAGE_DOCTOR_CLASH_APPOINTMENT);
        }
        if (patient.hasClashForAppointment(appointment)) {
            throw new CommandException(MESSAGE_PATIENT_CLASH_APPOINTMENT);
        }

        doctor.addUpcomingAppointment(appointment);
        patient.addUpcomingAppointment(appointment);
        model.incrementAppointmentCounter();
        model.addAppointment(appointment);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(patient));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && patientName.equals(((AddAppointmentCommand) other).patientName)
                && doctorName.equals(((AddAppointmentCommand) other).doctorName)
                && dateTime.equals(((AddAppointmentCommand) other).dateTime));
    }
}
