package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRIVER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VOLUNTEER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VOLUNTEER;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditVolunteerDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;
import seedu.address.testutil.VolunteerBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void execute_allFieldsSpecifiedUnfilteredVolunteerList_success() {
        Volunteer editedVolunteer = new VolunteerBuilder().build();
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(editedVolunteer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_VOLUNTEER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateVolunteer(model.getFilteredVolunteerList().get(0), editedVolunteer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredVolunteerList_success() {
        Index indexLastVolunteer = Index.fromOneBased(model.getFilteredVolunteerList().size());
        Volunteer lastVolunteer = model.getFilteredVolunteerList().get(indexLastVolunteer.getZeroBased());

        VolunteerBuilder volunteerInList = new VolunteerBuilder(lastVolunteer);
        Volunteer editedVolunteer = volunteerInList.withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_DRIVER).build();

        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_DRIVER).build();
        EditCommand editCommand = new EditCommand(indexLastVolunteer, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateVolunteer(lastVolunteer, editedVolunteer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredVolunteerList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_VOLUNTEER,
                new EditVolunteerDescriptor());
        Volunteer editedVolunteer = model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredVolunteerList_success() {
        showVolunteerAtIndex(model, INDEX_FIRST_VOLUNTEER);

        Volunteer volunteerInFilteredList = model.getFilteredVolunteerList()
                .get(INDEX_FIRST_VOLUNTEER.getZeroBased());
        Volunteer editedVolunteer = new VolunteerBuilder(volunteerInFilteredList)
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_VOLUNTEER,
                new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateVolunteer(model.getFilteredVolunteerList().get(0), editedVolunteer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVolunteerUnfilteredList_failure() {
        Volunteer firstVolunteer = model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased());
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(firstVolunteer).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_VOLUNTEER, descriptor);

        assertCommandFailure(editCommand, model, commandHistory,
                EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);
    }

    @Test
    public void execute_duplicateVolunteerFilteredList_failure() {
        showVolunteerAtIndex(model, INDEX_FIRST_VOLUNTEER);

        // edit volunteer in filtered list into a duplicate in address book
        Volunteer volunteerInList = model.getAddressBook().getVolunteerList()
                .get(INDEX_SECOND_VOLUNTEER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_VOLUNTEER,
                new EditVolunteerDescriptorBuilder(volunteerInList).build());

        assertCommandFailure(editCommand, model, commandHistory,
                EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);
    }

    @Test
    public void execute_invalidVolunteerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidVolunteerIndexFilteredList_failure() {
        showVolunteerAtIndex(model, INDEX_FIRST_VOLUNTEER);
        Index outOfBoundIndex = INDEX_SECOND_VOLUNTEER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getVolunteerList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredVolunteerList_success() throws Exception {
        Volunteer editedVolunteer = new VolunteerBuilder().build();
        Volunteer volunteerToEdit = model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased());
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(editedVolunteer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_VOLUNTEER, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateVolunteer(volunteerToEdit, editedVolunteer);
        expectedModel.commitAddressBook();

        // edit -> first volunteer edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered volunteer list to show all volunteers
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first volunteer edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredVolunteerList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Volunteer} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited volunteer in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the volunteer object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameVolunteerEdited() throws Exception {
        Volunteer editedVolunteer = new VolunteerBuilder().build();
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(editedVolunteer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_VOLUNTEER, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showVolunteerAtIndex(model, INDEX_SECOND_VOLUNTEER);
        Volunteer volunteerToEdit = model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased());
        expectedModel.updateVolunteer(volunteerToEdit, editedVolunteer);
        expectedModel.commitAddressBook();

        // edit -> edits second volunteer in unfiltered volunteer list / first volunteer in filtered volunteer list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered volunteer list to show all volunteers
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased()), volunteerToEdit);
        // redo -> edits same second volunteer in unfiltered volunteer list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_VOLUNTEER, DESC_AMY);

        // same values -> returns true
        EditVolunteerDescriptor copyDescriptor = new EditVolunteerDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_VOLUNTEER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_VOLUNTEER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_VOLUNTEER, DESC_BOB)));
    }

}
