package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE;
import static seedu.address.testutil.TypicalPatientsAndDoctors.FIONA;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE;
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
    public void execute_validDoctorUnfilteredList_success() {
        Doctor doctorToDelete = GEORGE;
        assertExecutionSuccess(doctorToDelete);
    }

    @Test
    public void execute_invalidDoctorUnfilteredList_failure() {
        // invalid name
        assertCommandFailure(new ViewDoctorCommand(new Name("JASKLFJA12412445")),
                model, commandHistory, ViewDoctorCommand.MESSAGE_INVALID_DOCTOR);

        // not Doctor
        assertExecutionFailure(ALICE, ViewDoctorCommand.MESSAGE_INVALID_DOCTOR);
    }

    @Test
    public void equals() {
        ViewDoctorCommand viewFirstDoctorCommand = new ViewDoctorCommand(GEORGE.getName());
        ViewDoctorCommand viewSecondDoctorCommand = new ViewDoctorCommand(FIONA.getName());

        // same object -> returns true
        assertTrue(viewFirstDoctorCommand.equals(viewFirstDoctorCommand));

        // same values -> returns true
        ViewDoctorCommand viewFirstDoctorCommandCopy = new ViewDoctorCommand(GEORGE.getName());
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
    private void assertExecutionSuccess(Doctor Doctor) {
        ViewDoctorCommand viewDoctorCommand = new ViewDoctorCommand(Doctor.getName());
        String expectedMessage = String.format(ViewDoctorCommand.MESSAGE_SUCCESS, Doctor.getName().toString());

        assertCommandSuccess(viewDoctorCommand, model, commandHistory, expectedMessage, expectedModel);

        PersonPanelSelectionChangedEvent lastEvent =
                (PersonPanelSelectionChangedEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(Doctor, lastEvent.getNewSelection());
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
