package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.FIONA_DOCTOR;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE_DOCTOR;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewDoctorCommand}.
 */
public class ViewDoctorCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validDoctor_success() {
        Doctor doctorToDelete = GEORGE_DOCTOR;
        assertExecutionSuccess(doctorToDelete);
    }

    @Test
    public void execute_invalidDoctor_failure() {
        // invalid name
        assertCommandFailure(new ViewDoctorCommand(new Name("JACKIE")),
                model, commandHistory, ViewDoctorCommand.MESSAGE_INVALID_DOCTOR);

        // not Doctor
        assertExecutionFailure(ALICE_PATIENT, ViewDoctorCommand.MESSAGE_INVALID_DOCTOR);
    }

    @Test
    public void equals() {
        ViewDoctorCommand viewFirstDoctorCommand = new ViewDoctorCommand(GEORGE_DOCTOR.getName());
        ViewDoctorCommand viewSecondDoctorCommand = new ViewDoctorCommand(FIONA_DOCTOR.getName());

        // same object -> returns true
        assertTrue(viewFirstDoctorCommand.equals(viewFirstDoctorCommand));

        // same values -> returns true
        ViewDoctorCommand viewFirstDoctorCommandCopy = new ViewDoctorCommand(GEORGE_DOCTOR.getName());
        assertTrue(viewFirstDoctorCommand.equals(viewFirstDoctorCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstDoctorCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstDoctorCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstDoctorCommand.equals(viewSecondDoctorCommand));
    }

    /**
     * Executes a {@code ViewDoctorCommand} with the given {@code name}, and checks
     * that {@code PersonPanelSelectionChangedEvent} is raised with the correct name.
     */
    private void assertExecutionSuccess(Doctor doctor) {
        ViewDoctorCommand viewDoctorCommand = new ViewDoctorCommand(doctor.getName());
        String expectedMessage = String.format(ViewDoctorCommand.MESSAGE_SUCCESS, doctor.getName());

        assertCommandSuccess(viewDoctorCommand, model, commandHistory, expectedMessage, expectedModel);

        PersonPanelSelectionChangedEvent lastEvent =
                (PersonPanelSelectionChangedEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(doctor, lastEvent.getNewSelection());
    }

    /**
     * Executes a {@code ViewDoctorCommand} with the given {@code name}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Person person, String expectedMessage) {
        ViewDoctorCommand viewDoctorCommand = new ViewDoctorCommand(person.getName());
        assertCommandFailure(viewDoctorCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
