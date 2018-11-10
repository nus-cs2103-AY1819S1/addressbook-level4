package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
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
import seedu.address.model.person.Person;

/**
 * Completes a patient's appointment in the health book.
 */
public class CompleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "complete-appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes a patient's appointment in the health book. "
            + "Parameters: "
            + "APPOINTMENT_ID \n"
            + "Example: " + COMMAND_WORD + " "
            + "10001 ";

    public static final String MESSAGE_SUCCESS = "Appointment completed";
    public static final String MESSAGE_INVALID_APPOINTMENT_INDEX = "AppointmentId is invalid";

    private final int appointmentId;

    /**
     * Creates an CompleteAppointmentCommand to add the specified {@code Appointment}
     */
    public CompleteAppointmentCommand(int appointmentId) {
        requireAllNonNull(appointmentId);
        this.appointmentId = appointmentId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Appointment> appointmentList = model.getFilteredAppointmentList();
        List<Person> personList = model.getFilteredPersonList();
        Patient patient = null;
        Doctor doctor = null;

        Appointment appointment = appointmentList.stream()
                .filter(appt -> appt.getAppointmentId() == appointmentId)
                .findFirst()
                .orElse(null);

        if (appointment == null) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_INDEX);
        }

        for (Person person : personList) {
            if (person instanceof Patient) {
                Iterator<Appointment> iterator = ((Patient) person).getUpcomingAppointments().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getAppointmentId() == appointmentId) {
                        patient = (Patient) person;
                    }
                }
            }
            if (person instanceof Doctor) {
                Iterator<Appointment> itr = ((Doctor) person).getUpcomingAppointments().iterator();
                while (itr.hasNext()) {
                    if (itr.next().getAppointmentId() == appointmentId) {
                        doctor = (Doctor) person;
                    }
                }
            }
            if (patient != null && doctor != null) {
                break;
            }
        }

        if (patient == null || doctor == null) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_INDEX);
        }

        try {
            googleCalendar.deleteAppointment(doctor.getName().toString() + doctor.getPhone().toString(),
                    appointment);
        } catch (GeneralSecurityException e) {
            throw new InvalidSecurityAccessException();
        } catch (IOException e) {
            throw new InvalidInputOutputException();
        }

        model.completeAppointment(appointment, patient, doctor);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(patient));
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteAppointmentCommand // instanceof handles nulls
                && (appointmentId == ((CompleteAppointmentCommand) other).appointmentId));
    }
}
