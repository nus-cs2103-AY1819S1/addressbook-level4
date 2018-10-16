package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BEN;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.Password;

//@@author jjlee050
public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void execute_validCredentials_returnTrue() {
        LoginCommand command = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));
        String expectedMessage = LoginCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);
    }

    @Test
    public void execute_invalidCredentials_returnFalse() {
        String expectedMessage = LoginCommand.MESSAGE_FAILURE;

        // different name
        LoginCommand command = new LoginCommand(
                new Doctor(ADAM.getId(), BEN.getName(), new Password("doctor1", false)));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);

        // different password
        command = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor2", false)));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);
    }

    @Test
    public void checkDoctorCred_nullDoctorsList_throwsNullPointerException() {
        LoginCommand command = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));

        thrown.expect(NullPointerException.class);
        command.checkDoctorCred(null);
    }

    @Test
    public void checkDoctorCred_emptyDoctorsList_returnFalse() {
        List<Doctor> emptyDoctorsList = new ArrayList<>();
        LoginCommand validCommand = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));

        assertFalse(validCommand.checkDoctorCred(emptyDoctorsList));
    }

    @Test
    public void checkDoctorCred_validCommand_returnTrue() {
        List<Doctor> doctorsList = model.getFilteredDoctorList();
        LoginCommand validCommand = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));

        assertTrue(validCommand.checkDoctorCred(doctorsList));
    }

    @Test
    public void checkDoctorCred_invalidCommand_returnFalse() {
        LoginCommand invalidCommand = new LoginCommand(
                new Doctor(ADAM.getId(), BEN.getName(), new Password("doctor1", false)));
        List<Doctor> doctorsList = model.getFilteredDoctorList();
        
        assertFalse(invalidCommand.checkDoctorCred(doctorsList));
    }

    @Test
    public void searchDoctor_nullDoctorListAndNullDoctorToSearch_throwsNullPointerException() {
        LoginCommand command = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));
        List<Doctor> emptyList = new ArrayList<>();
        
        thrown.expect(NullPointerException.class);
        // All fields are null.
        command.searchDoctor(null, null);
        
        // Some fields are null.
        command.searchDoctor(null, ADAM);
        command.searchDoctor(emptyList, null);
    }
    
    @Test
    public void searchDoctor_emptyDoctorList_returnNull() {
        LoginCommand command = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));
        List<Doctor> emptyList = new ArrayList<>();

        assertNull(command.searchDoctor(emptyList, ADAM));
        assertNull(command.searchDoctor(emptyList, BEN));
    }

    @Test
    public void searchDoctor_invalidDoctorList_throwsClassCastException() {
        LoginCommand command = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));
        List<Doctor> doctorsList = model.getFilteredDoctorList();

        thrown.expect(ClassCastException.class);

        // The doctor to found is not a doctor.
        command.searchDoctor(doctorsList, (Doctor) ALICE);
        command.searchDoctor(doctorsList, (Doctor) CARL);
    }

    @Test
    public void searchDoctor_noDoctorsFound_returnNull() {
        LoginCommand command = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));
        List<Doctor> doctorsList = Arrays.asList(BEN);

        // Cannot find ADAM in the doctorsList
        assertNull(command.searchDoctor(doctorsList, ADAM));
    }
    
    @Test
    public void searchDoctor_doctorFound_returnDoctor() {
        LoginCommand command = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));
        List<Doctor> doctorsList = model.getFilteredDoctorList();
        
        assertNotNull(command.searchDoctor(doctorsList, BEN));

    }

    @Test
    public void equals() {
        LoginCommand loginFirstCommand = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), ADAM.getPassword()));
        LoginCommand loginSecondCommand = new LoginCommand(
                new Doctor(BEN.getId(), BEN.getName(), BEN.getPassword()));

        // same object -> returns true
        assertTrue(loginFirstCommand.equals(loginFirstCommand));

        // same values -> returns true
        LoginCommand loginFirstCommandCopy = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), ADAM.getPassword()));
        assertTrue(loginFirstCommand.equals(loginFirstCommandCopy));

        // different types -> returns false
        assertFalse(loginFirstCommand.equals(1));

        // null -> returns false
        assertFalse(loginFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(loginFirstCommand.equals(loginSecondCommand));
    }
}
