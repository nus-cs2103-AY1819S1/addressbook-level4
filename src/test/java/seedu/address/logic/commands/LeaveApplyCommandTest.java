package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.LeaveApplicationBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for LeaveApplyCommand.
 */
public class LeaveApplyCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
            getTypicalArchiveList(), new UserPrefs());

    @Before
    public void setUp() {
        model.setLoggedInUser(new User(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())));
    }

    @Test
    public void constructor_nullLeaveApplication_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LeaveApplyCommand(null);
    }

    @Test
    public void runBody_addLeaveApplicationSuccessful() throws Exception {
        LeaveApplication validLeaveApplication = new LeaveApplicationBuilder().build();
        LeaveApplyCommand command = new LeaveApplyCommand(validLeaveApplication);

        Person firstPerson = model.getLoggedInUser().getPerson();
        List<LeaveApplication> leaveApplications = new ArrayList<>(firstPerson.getLeaveApplications());
        leaveApplications.add(validLeaveApplication);
        Person editedFirstPerson = new PersonBuilder(firstPerson).withLeaveApplications(leaveApplications).build();

        String expectedMessage = String.format(LeaveApplyCommand.MESSAGE_SUCCESS, validLeaveApplication);
        String.format(LeaveApplyCommand.MESSAGE_SUCCESS, validLeaveApplication);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
                getTypicalArchiveList(), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedFirstPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validLeaveApplication_success() throws Exception {
        LeaveApplication validLeaveApplication = new LeaveApplicationBuilder().build();
        LeaveApplyCommand command = new LeaveApplyCommand(validLeaveApplication);

        Person firstPerson = model.getLoggedInUser().getPerson();
        List<LeaveApplication> leaveApplications = new ArrayList<>(firstPerson.getLeaveApplications());
        leaveApplications.add(validLeaveApplication);
        Person editedFirstPerson = new PersonBuilder(firstPerson).withLeaveApplications(leaveApplications).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedFirstPerson);
        expectedModel.commitAddressBook();

        // edit -> first person applied for leave
        command.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        LeaveApplication validLeaveApplication = new LeaveApplicationBuilder().build();
        LeaveApplication anotherValidLeaveApplicationWithSameFields = new LeaveApplicationBuilder().build();
        LeaveApplication anotherValidLeaveApplicationWithDifferentFields = new LeaveApplicationBuilder()
                .withDescription("Diff").build();
        final LeaveApplyCommand standardCommand = new LeaveApplyCommand(validLeaveApplication);

        // same values -> returns true
        assertTrue(standardCommand.equals(new LeaveApplyCommand(anotherValidLeaveApplicationWithSameFields)));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different leave application -> returns false
        assertFalse(standardCommand.equals(new LeaveApplyCommand(anotherValidLeaveApplicationWithDifferentFields)));
    }

}
