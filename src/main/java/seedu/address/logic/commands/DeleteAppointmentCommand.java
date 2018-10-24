package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * Adds a patient's appointment to the health book.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "delete-appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a patient's appointment in the health book. "
            + "Parameters: "
            + "APPOINTMENT_ID \n"
            + "Example: " + COMMAND_WORD + " "
            + "3 ";

    public static final String MESSAGE_SUCCESS = "Appointment deleted";
    public static final String MESSAGE_INVALID_APPOINTMENT_INDEX = "AppointmentId is invalid";

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
        Set<Tag> mySet = new HashSet<Tag>();
        Appointment appointment = new Appointment(new Doctor(new Name("elroy"), new Phone("123412324"),
                new Email("erear@g.com"), new Address("2134"), new Remark("asfasf"), mySet),
                LocalDateTime.of(2017, 1, 1, 1, 1,1));

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
                Iterator<Appointment> itr = ((Patient) person).getUpcomingAppointments().iterator();
                while (itr.hasNext()) {
                    if (itr.next().getAppointmentId() == appointmentId) {
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

        model.deleteAppointment(appointment);
        patient.deleteAppointment(appointment);
        doctor.deleteAppointment(appointment);
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
