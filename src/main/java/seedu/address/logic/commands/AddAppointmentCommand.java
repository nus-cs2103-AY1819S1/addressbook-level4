package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;

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

/**
 * Adds a patient's appointment to the health book.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "add-appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient's appointment to the health book. "
            + "Parameters: "
            + PREFIX_PATIENT_NAME + "PATIENT_NAME "
            + PREFIX_DOCTOR_NAME + "DOCTOR_NAME "
            + PREFIX_DATE_TIME + "DATE_TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_NAME + "John Doe "
            + PREFIX_DOCTOR_NAME + "Mary Jane "
            + PREFIX_DATE_TIME + "2018-10-17 15:00 ";

    public static final String MESSAGE_SUCCESS = "New appointment added";
    public static final String MESSAGE_INVALID_PATIENT = "This patient does not exist in the HealthBook";
    public static final String MESSAGE_INVALID_DOCTOR = "This doctor does not exist in the HealthBook";
    // TODO - add messages for various cases (e.g. conflict in schedule) here when google calendar is up

    private final Name patientName;
    private final Name doctorName;
    private final LocalDateTime dateTime;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Name patientName, Name doctorName, LocalDateTime dateTime) {
        requireAllNonNull(patientName, doctorName, dateTime);
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.dateTime = dateTime;
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
                patient = (Patient) person;
            }
            if (person.getName().equals(doctorName) && person instanceof Doctor) {
                doctor = (Doctor) person;
            }
            if (patient != null && doctor != null) {
                break;
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
            googleCalendar.addAppointment(doctor.getName().toString(), appointment);
        } catch (GeneralSecurityException e) {
            throw new InvalidSecurityAccessException();
        } catch (IOException e) {
            throw new InvalidInputOutputException();
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
