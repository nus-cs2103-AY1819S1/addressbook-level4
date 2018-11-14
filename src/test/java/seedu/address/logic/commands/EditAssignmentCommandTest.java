package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENTNAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_B;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressbook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

public class EditAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Assignment editedAssignment = new AssignmentBuilder().build();
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder(editedAssignment).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, descriptor);

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateAssignment(model.getFilteredAssignmentList().get(0), editedAssignment);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editAssignmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAssignment = Index.fromOneBased(model.getFilteredAssignmentList().size());
        Assignment lastAssignment = model.getFilteredAssignmentList().get(indexLastAssignment.getZeroBased());

        AssignmentBuilder assignmentInList = new AssignmentBuilder(lastAssignment);
        Assignment editedAssignment = assignmentInList.withName(VALID_ASSIGNMENTNAME_B)
                .withWeight(VALID_WEIGHT_B).build();

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder().withName(VALID_ASSIGNMENTNAME_B)
                .withWeight(VALID_WEIGHT_B).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(indexLastAssignment, descriptor);

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateAssignment(lastAssignment, editedAssignment);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editAssignmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT,
                new EditAssignmentDescriptor());
        Assignment editedAssignment = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editAssignmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAssignmentUnfilteredList_failure() {
        Assignment firstAssignment = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder(firstAssignment).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_SECOND_ASSIGNMENT, descriptor);

        assertCommandFailure(editAssignmentCommand, model, commandHistory,
                EditAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    @Test
    public void execute_invalidAssignmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withName(VALID_ASSIGNMENTNAME_B).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Assignment editedAssignment = new AssignmentBuilder().build();
        Assignment assignmentToEdit = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder(editedAssignment).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateAssignment(assignmentToEdit, editedAssignment);
        expectedModel.commitAddressBook();

        // edit -> first assignment edited
        editAssignmentCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered assignment list to show all assignments
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first assignment edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withName(VALID_ASSIGNMENTNAME_B).build();
        EditAssignmentCommand editAssignmentCommand = new EditAssignmentCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editAssignmentCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final EditAssignmentCommand standardCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, DESC_A);

        // same values -> returns true
        EditAssignmentDescriptor copyDescriptor = new EditAssignmentDescriptor(DESC_A);
        EditAssignmentCommand commandWithSameValues = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(INDEX_SECOND_ASSIGNMENT, DESC_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, DESC_B)));
    }

}
