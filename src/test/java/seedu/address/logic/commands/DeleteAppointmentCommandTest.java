package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.CARL_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.HELENA_DOCTOR;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;


/**
 * Contains integration tests and unit tests for DeleteAppointmentCommand.
 */
public class DeleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deleteAppointment_success() {
        // add appointment into expectedModel and actualModel first
        Appointment appointment = new Appointment(10000, HELENA_DOCTOR.getName().toString(),
                CARL_PATIENT.getName().toString(), LocalDateTime.of(2018, 10, 17, 18, 0));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addAppointment(appointment);
        expectedModel.incrementAppointmentCounter();
        expectedModel.commitAddressBook();
        model.addAppointment(appointment);
        model.incrementAppointmentCounter();
        model.commitAddressBook();

        // delete
        expectedModel.deleteAppointment(appointment, CARL_PATIENT, HELENA_DOCTOR);
        expectedModel.commitAddressBook();

        CARL_PATIENT.addUpcomingAppointment(appointment);
        HELENA_DOCTOR.addUpcomingAppointment(appointment);
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(appointment.getAppointmentId());

        String expectedMessage = DeleteAppointmentCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(deleteAppointmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAppointment_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(1);

        assertCommandFailure(deleteAppointmentCommand,
                model, commandHistory, deleteAppointmentCommand.MESSAGE_INVALID_APPOINTMENT_INDEX);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteAppointmentFirstCommand =
                new DeleteAppointmentCommand(10000);
        DeleteAppointmentCommand deleteAppointmentSecondCommand =
                new DeleteAppointmentCommand(10001);

        // same object -> returns true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy =
                new DeleteAppointmentCommand(10000);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals("ABC"));

        // null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));
    }

}
