package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE;
import static seedu.address.testutil.TypicalPatientsAndDoctors.BENSON;
import static seedu.address.testutil.TypicalPatientsAndDoctors.CARL;
import static seedu.address.testutil.TypicalPatientsAndDoctors.FIONA;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE;
import static seedu.address.testutil.TypicalPatientsAndDoctors.HELENA;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;


/**
 * Contains integration tests and unit tests for AddAppointmentCommand.
 */
public class AddAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addAppointment_success() {
        Appointment toAdd = new Appointment(10000, HELENA.getName().toString(),
                CARL.getName().toString(), LocalDateTime.of(2018, 10, 17, 18, 0));
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(CARL.getName(), null, HELENA.getName(),
                        null, LocalDateTime.of(2018, 10, 17, 18, 0));

        String expectedMessage = AddAppointmentCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addAppointment(toAdd);
        expectedModel.incrementAppointmentCounter();
        expectedModel.commitAddressBook();

        assertCommandSuccess(addAppointmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatient_throwsCommandException() {
        Name patientName = new Name("ASFASFASF");
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(patientName, null, GEORGE.getName(), GEORGE.getPhone(),
                        LocalDateTime.of(2018, 10, 17, 18, 0));

        assertCommandFailure(addAppointmentCommand,
                model, commandHistory, AddAppointmentCommand.MESSAGE_INVALID_PATIENT);
    }

    @Test
    public void execute_invalidDoctor_throwsCommandException() {
        Name doctorName = new Name("ASFASFASF");
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(ALICE.getName(), null, doctorName, null,
                        LocalDateTime.of(2018, 10, 17, 18, 0));

        assertCommandFailure(addAppointmentCommand,
                model, commandHistory, AddAppointmentCommand.MESSAGE_INVALID_DOCTOR);
    }

    @Test
    public void execute_patientClashAppointment_throwsCommandException() {
        Model modifiableModel = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
        CommandHistory modifiableCommandHistory = new CommandHistory();
        Appointment toAdd = new Appointment(10000, GEORGE.getName().toString(),
                ALICE.getName().toString(), LocalDateTime.of(2018, 10, 17, 18, 0));
        ALICE.addUpcomingAppointment(toAdd);
        modifiableModel.addAppointment(toAdd);
        modifiableModel.incrementAppointmentCounter();
        modifiableModel.commitAddressBook();

        // another appointment on the same datetime for same patient but different doctor
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(ALICE.getName(), ALICE.getPhone(), FIONA.getName(), FIONA.getPhone(),
                        LocalDateTime.of(2018, 10, 17, 18, 0));
        assertCommandFailure(addAppointmentCommand,
                modifiableModel, modifiableCommandHistory, AddAppointmentCommand.MESSAGE_PATIENT_CLASH_APPOINTMENT);
    }

    @Test
    public void execute_doctorClashAppointment_throwsCommandException() {
        Model modifiableModel = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
        CommandHistory modifiableCommandHistory = new CommandHistory();
        Appointment toAdd = new Appointment(10000, GEORGE.getName().toString(),
                ALICE.getName().toString(), LocalDateTime.of(2018, 10, 17, 18, 0));
        GEORGE.addUpcomingAppointment(toAdd);
        modifiableModel.addAppointment(toAdd);
        modifiableModel.incrementAppointmentCounter();
        modifiableModel.commitAddressBook();

        // another appointment on the same datetime for same doctor but different patient
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(BENSON.getName(), BENSON.getPhone(), GEORGE.getName(), GEORGE.getPhone(),
                        LocalDateTime.of(2018, 10, 17, 18, 0));
        assertCommandFailure(addAppointmentCommand,
                modifiableModel, modifiableCommandHistory, AddAppointmentCommand.MESSAGE_DOCTOR_CLASH_APPOINTMENT);
    }

    @Test
    public void equals() {
        AddAppointmentCommand addAppointmentFirstCommand =
                new AddAppointmentCommand(ALICE.getName(), ALICE.getPhone(), GEORGE.getName(), GEORGE.getPhone(),
                        LocalDateTime.of(2018, 10, 17, 18, 0));
        AddAppointmentCommand addAppointmentSecondCommand =
                new AddAppointmentCommand(BENSON.getName(), BENSON.getPhone(), GEORGE.getName(), GEORGE.getPhone(),
                        LocalDateTime.of(2018, 10, 17, 18, 0));

        // same object -> returns true
        assertTrue(addAppointmentFirstCommand.equals(addAppointmentFirstCommand));

        // same values -> returns true
        AddAppointmentCommand addAppointmentFirstCommandCopy =
                new AddAppointmentCommand(ALICE.getName(), ALICE.getPhone(), GEORGE.getName(), GEORGE.getPhone(),
                        LocalDateTime.of(2018, 10, 17, 18, 0));
        assertTrue(addAppointmentFirstCommand.equals(addAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(addAppointmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addAppointmentFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addAppointmentFirstCommand.equals(addAppointmentSecondCommand));
    }

}
