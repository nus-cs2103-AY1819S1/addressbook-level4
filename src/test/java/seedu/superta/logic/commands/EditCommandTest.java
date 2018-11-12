package seedu.superta.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.superta.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.superta.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Test;

import seedu.superta.commons.core.Messages;
import seedu.superta.commons.core.index.Index;
import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.student.Student;
import seedu.superta.testutil.EditStudentDescriptorBuilder;
import seedu.superta.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student editedStudent = new StudentBuilder().withStudentId("A0232413E").build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new SuperTaClient(model.getSuperTaClient()), new UserPrefs());
        expectedModel.updateStudent(model.getFilteredStudentList().get(0), editedStudent);
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastPerson.getZeroBased());

        StudentBuilder personInList = new StudentBuilder(lastStudent);
        Student editedStudent = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new SuperTaClient(model.getSuperTaClient()), new UserPrefs());
        expectedModel.updateStudent(lastStudent, editedStudent);
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditStudentDescriptor());
        Student editedStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new SuperTaClient(model.getSuperTaClient()), new UserPrefs());
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList).withName("Test Person").build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditStudentDescriptorBuilder().withName("Test Person").build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new SuperTaClient(model.getSuperTaClient()), new UserPrefs());
        expectedModel.updateStudent(model.getFilteredStudentList().get(0), editedStudent);
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(firstStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit student in filtered list into a duplicate in address book
        Student studentInList = model.getSuperTaClient().getStudentList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditStudentDescriptorBuilder(studentInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSuperTaClient().getStudentList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Student editedStudent = new StudentBuilder().build();
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new SuperTaClient(model.getSuperTaClient()), new UserPrefs());
        expectedModel.updateStudent(studentToEdit, editedStudent);
        expectedModel.commitSuperTaClient();

        // edit -> first student edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered student list to show all persons
        expectedModel.undoSuperTaClient();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first student edited again
        expectedModel.redoSuperTaClient();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Student} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited student in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the student object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Student editedStudent = new StudentBuilder().withStudentId("A2323232K").build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new SuperTaClient(model.getSuperTaClient()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updateStudent(studentToEdit, editedStudent);
        expectedModel.commitSuperTaClient();

        // edit -> edits second student in unfiltered student list / first student in filtered student list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered student list to show all persons
        expectedModel.undoSuperTaClient();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredStudentList().get(INDEX_FIRST_PERSON.getZeroBased()), studentToEdit);
        // redo -> edits same second student in unfiltered student list
        expectedModel.redoSuperTaClient();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditStudentDescriptor copyDescriptor = new EditStudentDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
