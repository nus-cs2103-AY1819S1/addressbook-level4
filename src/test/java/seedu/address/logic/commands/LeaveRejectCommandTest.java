package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLeaveApplicationAtIndex;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.StatusEnum;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.LeaveApplicationBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for LeaveRejectCommand.
 */
public class LeaveRejectCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
            getTypicalArchiveList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model.setLoggedInUser(User.getAdminUser());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        LeaveRejectCommand leaveRejectCommand = new LeaveRejectCommand(INDEX_FIRST_PERSON);
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_SECOND_PERSON.getZeroBased())).build();
        List<LeaveApplication> leaveApplications = originalPerson.getLeaveApplications();
        LeaveApplication leaveApplication = leaveApplications.get(0);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        String originalUsername = originalPerson.getUsername().username;
        String originalPassword = originalPerson.getPassword().plaintext;
        leaveApplication = new LeaveApplicationBuilder(leaveApplication).withStatus(StatusEnum.Status.REJECTED).build();
        leaveApplications = new ArrayList<>(Arrays.asList(leaveApplication));
        Person editedPerson = new PersonBuilder(originalPerson).withUsername(originalUsername)
                .withPassword(originalPassword).withLeaveApplications(leaveApplications).build();

        String expectedMessage = String.format(LeaveRejectCommand.MESSAGE_LEAVE_REJECT_SUCCESS, leaveApplication);

        expectedModel.updatePerson(originalPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(leaveRejectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLeaveApplicationAtIndex(model, INDEX_FIRST_PERSON);

        LeaveRejectCommand leaveRejectCommand = new LeaveRejectCommand(INDEX_FIRST_PERSON);
        Person originalPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_SECOND_PERSON.getZeroBased())).build();
        List<LeaveApplication> leaveApplications = originalPerson.getLeaveApplications();
        LeaveApplication leaveApplication = leaveApplications.get(0);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        showLeaveApplicationAtIndex(expectedModel, INDEX_FIRST_PERSON);

        String originalUsername = originalPerson.getUsername().username;
        String originalPassword = originalPerson.getPassword().plaintext;
        leaveApplication = new LeaveApplicationBuilder(leaveApplication).withStatus(StatusEnum.Status.REJECTED).build();
        leaveApplications = new ArrayList<>(Arrays.asList(leaveApplication));
        Person editedPerson = new PersonBuilder(originalPerson).withUsername(originalUsername)
                .withPassword(originalPassword).withLeaveApplications(leaveApplications).build();

        String expectedMessage = String.format(LeaveRejectCommand.MESSAGE_LEAVE_REJECT_SUCCESS, leaveApplication);

        expectedModel.updatePerson(originalPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(leaveRejectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidLeaveIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLeaveApplicationList().size() + 1);
        LeaveRejectCommand leaveRejectCommand = new LeaveRejectCommand(outOfBoundIndex);

        assertCommandFailure(leaveRejectCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidLeaveIndexFilteredList_failure() {
        showLeaveApplicationAtIndex(model, INDEX_SECOND_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLeaveApplicationList().size());

        LeaveRejectCommand editCommand = new LeaveRejectCommand(outOfBoundIndex);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        LeaveRejectCommand leaveRejectCommand = new LeaveRejectCommand(INDEX_FIRST_PERSON);

        Person originalPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        List<LeaveApplication> leaveApplications = originalPerson.getLeaveApplications();
        LeaveApplication leaveApplication = leaveApplications.get(0);

        String originalUsername = originalPerson.getUsername().username;
        String originalPassword = originalPerson.getPassword().plaintext;
        leaveApplication = new LeaveApplicationBuilder(leaveApplication).withStatus(StatusEnum.Status.REJECTED).build();
        leaveApplications = new ArrayList<>(Arrays.asList(leaveApplication));
        Person editedPerson = new PersonBuilder(originalPerson).withUsername(originalUsername)
                .withPassword(originalPassword).withLeaveApplications(leaveApplications).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updatePerson(originalPerson, editedPerson);
        expectedModel.commitAddressBook();

        // edit -> first leave rejected
        leaveRejectCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first leave edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLeaveApplicationList().size() + 1);
        LeaveRejectCommand editCommand = new LeaveRejectCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final LeaveRejectCommand standardCommand = new LeaveRejectCommand(INDEX_FIRST_PERSON);

        // same values -> returns true
        LeaveRejectCommand commandWithSameValues = new LeaveRejectCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new LeaveRejectCommand(INDEX_SECOND_PERSON)));
    }

}
