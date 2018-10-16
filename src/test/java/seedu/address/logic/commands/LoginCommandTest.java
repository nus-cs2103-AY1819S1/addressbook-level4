package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.BEN;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
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
    public void checkDoctorCred_validCommandWithValidDoctorsList_returnTrue() {
        List<Doctor> validDoctorsList = model.getFilteredDoctorList();
        LoginCommand validCommand = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));

        assertTrue(validCommand.checkDoctorCred(validDoctorsList));
    }

    @Test
    public void checkDoctorCred_emptyDoctorsList_returnFalse() {
        List<Doctor> emptyDoctorsList = new ArrayList<>();
        LoginCommand validCommand = new LoginCommand(
                new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));

        assertFalse(validCommand.checkDoctorCred(emptyDoctorsList));
    }

    @Test
    public void checkDoctorCred_validCommandWithInvalidDoctorsList_returnFalse() {
        LoginCommand validCommand = new LoginCommand(
                    new Doctor(ADAM.getId(), ADAM.getName(), new Password("doctor1", false)));
        List<Doctor> invalidDoctorsList = Collections.singletonList(BEN);

        assertFalse(validCommand.checkDoctorCred(invalidDoctorsList));
    }

    @Test
    public void checkDoctorCred_invalidCommandWithValidDoctorsList_returnFalse() {
        LoginCommand invalidCommand = new LoginCommand(
                new Doctor(ADAM.getId(), BEN.getName(), new Password("doctor1", false)));
        List<Doctor> validDoctorsList = model.getFilteredDoctorList();
        
        assertFalse(invalidCommand.checkDoctorCred(validDoctorsList));

        
    }
        
    @Test
    public void checkDoctorCred_invalidCommandWithInvalidDoctorsList_returnFalse() {
        LoginCommand invalidCommand = new LoginCommand(
                new Doctor(ADAM.getId(), BEN.getName(), new Password("doctor1", false)));
        List<Doctor> invalidDoctorsList = Collections.singletonList(BEN);

        assertFalse(invalidCommand.checkDoctorCred(invalidDoctorsList));
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
