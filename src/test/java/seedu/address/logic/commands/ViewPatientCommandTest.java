package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.BENSON_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE_DOCTOR;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewPatientCommand}.
 */
public class ViewPatientCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validPatient_success() {
        Patient patientToDelete = ALICE_PATIENT;
        assertExecutionSuccess(patientToDelete);
    }

    @Test
    public void execute_invalidPatient_failure() {
        // invalid name
        assertCommandFailure(new ViewPatientCommand(new Name("JASKLFJA12412445")),
                model, commandHistory, ViewPatientCommand.MESSAGE_INVALID_PATIENT);

        // not patient
        assertExecutionFailure(GEORGE_DOCTOR, ViewPatientCommand.MESSAGE_INVALID_PATIENT);
    }

    @Test
    public void equals() {
        ViewPatientCommand viewFirstPatientCommand = new ViewPatientCommand(ALICE_PATIENT.getName());
        ViewPatientCommand viewSecondPatientCommand = new ViewPatientCommand(BENSON_PATIENT.getName());

        // same object -> returns true
        assertTrue(viewFirstPatientCommand.equals(viewFirstPatientCommand));

        // same values -> returns true
        ViewPatientCommand viewFirstPatientCommandCopy = new ViewPatientCommand(ALICE_PATIENT.getName());
        assertTrue(viewFirstPatientCommand.equals(viewFirstPatientCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstPatientCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstPatientCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstPatientCommand.equals(viewSecondPatientCommand));
    }

    /**
     * Executes a {@code ViewPatientCommand} with the given {@code name}, and checks
     * that {@code PersonPanelSelectionChangedEvent} is raised with the correct name.
     */
    private void assertExecutionSuccess(Patient patient) {
        ViewPatientCommand viewPatientCommand = new ViewPatientCommand(patient.getName());
        String expectedMessage = String.format(ViewPatientCommand.MESSAGE_SUCCESS, patient.getName());

        assertCommandSuccess(viewPatientCommand, model, commandHistory, expectedMessage, expectedModel);

        PersonPanelSelectionChangedEvent lastEvent =
                (PersonPanelSelectionChangedEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(patient, lastEvent.getNewSelection());
    }

    /**
     * Executes a {@code ViewPatientCommand} with the given {@code name}, and checks that a {@co    de CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Person person, String expectedMessage) {
        ViewPatientCommand viewPatientCommand = new ViewPatientCommand(person.getName());
        assertCommandFailure(viewPatientCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
