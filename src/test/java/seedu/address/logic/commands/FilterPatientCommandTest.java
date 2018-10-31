package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.TagContainsDoctorPredicate;
import seedu.address.model.tag.TagContainsPatientPredicate;

public class FilterPatientCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void filterMultiplePatients() {
        FilterPatientCommand filterPatientCommand = new FilterPatientCommand();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(filterPatientCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void filterZeroPatient() {
        final TagContainsDoctorPredicate predicate = new TagContainsDoctorPredicate();
        model.updateFilteredPersonList(predicate);
        FilterPatientCommand filterPatientCommand = new FilterPatientCommand();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        final TagContainsPatientPredicate predicate2 = new TagContainsPatientPredicate();
        expectedModel.updateFilteredPersonList(predicate2);
        String expectedMessage = String.format(
                Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(filterPatientCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final FilterPatientCommand standardCommand = new FilterPatientCommand();

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}
