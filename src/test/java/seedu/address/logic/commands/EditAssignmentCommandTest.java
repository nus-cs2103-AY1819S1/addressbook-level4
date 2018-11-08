package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.DESC_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAssignmentAtIndex;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.User;
import seedu.address.model.project.Assignment;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
            getTypicalArchiveList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model.setLoggedInUser(User.getAdminUser());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Assignment editedAssignment = new AssignmentBuilder().build();
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(editedAssignment).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        String originalAssignment = model.getFilteredAssignmentList().get(0).getProjectName().fullProjectName;
        String originalAuthor = model.getFilteredAssignmentList().get(0).getAuthor().fullName;
        String originalDescription = model.getFilteredAssignmentList().get(0).getDescription().value;
        editedAssignment = new AssignmentBuilder(editedAssignment).withAssignmentName(originalAssignment)
                .withAuthor(originalAuthor).withDescription(originalDescription).build();

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        expectedModel.updateAssignment(model.getFilteredAssignmentList().get(0), editedAssignment);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAssignment = Index.fromOneBased(model.getFilteredAssignmentList().size());
        Assignment lastAssignment = model.getFilteredAssignmentList().get(indexLastAssignment.getZeroBased());

        AssignmentBuilder assignmentInList = new AssignmentBuilder(lastAssignment);
        Assignment editedAssignment = assignmentInList.withAuthor(VALID_NAME_AMY).build();

        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withAuthor(VALID_NAME_AMY).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(indexLastAssignment, descriptor);

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updateAssignment(lastAssignment, editedAssignment);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditAssignmentCommand editCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT,
                new EditAssignmentCommand.EditAssignmentDescriptor());
        Assignment editedAssignment = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showAssignmentAtIndex(model, INDEX_FIRST_ASSIGNMENT);

        Assignment assignmentInFilteredList = model.getFilteredAssignmentList()
                .get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        Assignment editedAssignment = new AssignmentBuilder(assignmentInFilteredList)
                .withAssignmentName(VALID_PROJECT_OASIS).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT,
                new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_PROJECT_OASIS).build());

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS,
                editedAssignment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updateAssignment(model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased()),
                editedAssignment);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAssignmentUnfilteredList_success() {
        Assignment firstAssignment = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(firstAssignment).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(INDEX_SECOND_ASSIGNMENT, descriptor);

        Assignment secondAssignment = model.getFilteredAssignmentList().get(INDEX_SECOND_ASSIGNMENT.getZeroBased());
        Assignment editedAssignment = new AssignmentBuilder(firstAssignment).withAssignmentName(secondAssignment
                .getProjectName().fullProjectName)
                .withAuthor(secondAssignment.getAuthor().fullName).build();

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updateAssignment(model.getFilteredAssignmentList().get(INDEX_SECOND_ASSIGNMENT.getZeroBased()),
                editedAssignment);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAssignmentFilteredList_success() {
        showAssignmentAtIndex(model, INDEX_FIRST_ASSIGNMENT);

        // edit assignment in filtered list into a duplicate in address book
        Assignment assignmentInList = model.getAssignmentList().getAssignmentList()
                .get(INDEX_SECOND_ASSIGNMENT.getZeroBased());
        EditAssignmentCommand editCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT,
                new EditAssignmentDescriptorBuilder(assignmentInList).build());

        Assignment firstAssignment = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        Assignment editedAssignment = new AssignmentBuilder(assignmentInList)
                .withAssignmentName(firstAssignment.getProjectName().fullProjectName)
                .withAuthor(firstAssignment.getAuthor().fullName).build();

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS, editedAssignment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updateAssignment(model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased()),
                editedAssignment);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAssignmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        EditAssignmentCommand.EditAssignmentDescriptor descriptor = new EditAssignmentDescriptorBuilder()
                .withAssignmentName(VALID_PROJECT_OASIS).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Assignment assignmentToEdit = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        Assignment editedAssignment = new AssignmentBuilder().withAssignmentName(assignmentToEdit.getProjectName()
                .fullProjectName).withAuthor(assignmentToEdit.getAuthor().fullName).build();
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(editedAssignment).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
        expectedModel.updateAssignment(assignmentToEdit, editedAssignment);
        expectedModel.commitAddressBook();

        // edit -> first person edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAssignmentList().size() + 1);
        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_PROJECT_OASIS).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Assignment} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited assignment in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the assignment object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        showAssignmentAtIndex(model, INDEX_SECOND_ASSIGNMENT);

        Assignment assignmentToEdit = model.getFilteredAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        Assignment editedAssignment =
                new AssignmentBuilder().withAssignmentName(assignmentToEdit.getProjectName().fullProjectName)
                .withAuthor(assignmentToEdit.getAuthor().fullName)
                .withDescription(assignmentToEdit.getDescription().value)
                .build();

        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder(editedAssignment).build();
        EditAssignmentCommand editCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());

        expectedModel.updateAssignment(assignmentToEdit, editedAssignment);
        expectedModel.commitAddressBook();

        // edit -> edits second assignment in unfiltered assignment list / first assignment in filtered assignment list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered assignment list to show all assignments
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> edits same second assignment in unfiltered assignment list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditAssignmentCommand standardCommand = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, DESC_OASIS);

        // same values -> returns true
        EditAssignmentCommand.EditAssignmentDescriptor copyDescriptor =
                new EditAssignmentCommand.EditAssignmentDescriptor(DESC_OASIS);
        EditAssignmentCommand commandWithSameValues = new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(INDEX_SECOND_ASSIGNMENT, DESC_FALCON)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAssignmentCommand(INDEX_FIRST_ASSIGNMENT, DESC_FALCON)));
    }
}
