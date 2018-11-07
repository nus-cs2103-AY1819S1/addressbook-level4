package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.BENSON_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.FIONA_DOCTOR;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE_DOCTOR;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.testutil.GoogleCalendarStub;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePersonCommandTest {

    private static final GoogleCalendarStub GOOGLE_CALENDAR_STUB = new GoogleCalendarStub();
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validPatient_success() {
        Patient patientToDelete = ALICE_PATIENT;
        DeletePatientCommand deletePatientCommand =
                new DeletePatientCommand(patientToDelete.getName());

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, patientToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(patientToDelete); // Test
        expectedModel.commitAddressBook();

        assertCommandSuccess(deletePatientCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatient_throwsCommandException() {
        // invalid name
        assertCommandFailure(new DeletePatientCommand(new Name("JASKLFJA12412445")),
                model, commandHistory, String.format(DeletePatientCommand.MESSAGE_INVALID_DELETE_PERSON, "Patient"));

        // not patient
        assertCommandFailure(new DeletePatientCommand(GEORGE_DOCTOR.getName()),
                model, commandHistory, String.format(DeletePatientCommand.MESSAGE_INVALID_DELETE_PERSON, "Patient"));
    }

    @Test
    public void execute_validDoctor_success() {
        Doctor doctorToDelete = GEORGE_DOCTOR;
        DeleteDoctorCommand deleteDoctorCommand =
                new DeleteDoctorCommand(doctorToDelete.getName());

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, doctorToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(doctorToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDoctor_throwsCommandException() {
        // invalid name
        assertCommandFailure(new DeleteDoctorCommand(new Name("JASKLFJA12412445")),
                model, commandHistory, String.format(DeleteDoctorCommand.MESSAGE_INVALID_DELETE_PERSON, "Doctor"));

        // not doctor
        assertCommandFailure(new DeleteDoctorCommand(ALICE_PATIENT.getName()),
                model, commandHistory, String.format(DeleteDoctorCommand.MESSAGE_INVALID_DELETE_PERSON, "Doctor"));
    }

    @Test
    public void executeUndoRedo_validPatient_success() throws Exception {
        Patient patientToDelete = ALICE_PATIENT;
        DeletePatientCommand deletePatientCommand =
                new DeletePatientCommand(patientToDelete.getName());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(patientToDelete); // Test
        expectedModel.commitAddressBook();

        // delete -> first person deleted
        deletePatientCommand.execute(model, commandHistory, GOOGLE_CALENDAR_STUB);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validDoctor_success() throws Exception {
        Doctor doctorToDelete = GEORGE_DOCTOR;
        DeleteDoctorCommand deleteDoctorCommand =
                new DeleteDoctorCommand(doctorToDelete.getName());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(doctorToDelete);
        expectedModel.commitAddressBook();

        // delete -> first person deleted
        deleteDoctorCommand.execute(model, commandHistory, GOOGLE_CALENDAR_STUB);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidPatient_failure() {
        // execution failed -> address book state not added into model
        assertCommandFailure(new DeletePatientCommand(new Name("JASKLFJA12412445")),
                model, commandHistory, String.format(DeletePatientCommand.MESSAGE_INVALID_DELETE_PERSON, "Patient"));

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidDoctor_failure() {
        // execution failed -> address book state not added into model
        assertCommandFailure(new DeleteDoctorCommand(new Name("JASKLFJA12412445")),
                model, commandHistory, String.format(DeleteDoctorCommand.MESSAGE_INVALID_DELETE_PERSON, "Doctor"));

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeletePatientCommand deleteFirstCommand = new DeletePatientCommand(ALICE_PATIENT.getName());
        DeletePatientCommand deleteSecondCommand = new DeletePatientCommand(BENSON_PATIENT.getName());
        DeleteDoctorCommand deleteThirdCommand = new DeleteDoctorCommand(FIONA_DOCTOR.getName());
        DeleteDoctorCommand deleteFourthCommand = new DeleteDoctorCommand(GEORGE_DOCTOR.getName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteThirdCommand.equals(deleteThirdCommand));

        // same values -> returns true
        DeletePatientCommand deleteFirstCommandCopy = new DeletePatientCommand(ALICE_PATIENT.getName());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
        DeleteDoctorCommand deleteThirdCommandCopy = new DeleteDoctorCommand(FIONA_DOCTOR.getName());
        assertTrue(deleteThirdCommandCopy.equals(deleteThirdCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteThirdCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
        assertFalse(deleteThirdCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
        assertFalse(deleteThirdCommand.equals(deleteFourthCommand));
    }
}
