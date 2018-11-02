package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandOccasionTestUtil.DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_STUDY;
import static seedu.address.logic.commands.CommandOccasionTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandOccasionTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandOccasionTestUtil.showOccasionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OCCASION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OCCASION;
import static seedu.address.testutil.TypicalOccasions.getTypicalOccasionsAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.testutil.OccasionBuilder;
import seedu.address.testutil.OccasionDescriptorBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for EditOccasionCommand.
 */
public class EditOccasionCommandTest {

    private Model model = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Occasion editedOccasion = new OccasionBuilder().build();
        OccasionDescriptor descriptor = new OccasionDescriptorBuilder(editedOccasion).build();
        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(INDEX_FIRST_OCCASION, descriptor);

        String expectedMessage = String.format(EditOccasionCommand.MESSAGE_EDIT_OCCASION_SUCCESS, editedOccasion);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateOccasion(model.getFilteredOccasionList().get(0), editedOccasion);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editOccasionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOccasion = Index.fromOneBased(model.getFilteredOccasionList().size());
        Occasion lastOccasion = model.getFilteredOccasionList().get(indexLastOccasion.getZeroBased());

        OccasionBuilder personInList = new OccasionBuilder(lastOccasion);
        Occasion editedOccasion = personInList.withOccasionName(VALID_OCCASIONNAME_TWO)
                .withOccasionDate(VALID_OCCASIONDATE_TWO).withTags(VALID_TAG_STUDY).build();

        OccasionDescriptor descriptor = new OccasionDescriptorBuilder().withOccasionName(VALID_OCCASIONNAME_TWO)
                .withOccasionDate(VALID_OCCASIONDATE_TWO).withTags(VALID_TAG_STUDY).build();
        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(indexLastOccasion, descriptor);

        String expectedMessage = String.format(EditOccasionCommand.MESSAGE_EDIT_OCCASION_SUCCESS, editedOccasion);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateOccasion(lastOccasion, editedOccasion);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editOccasionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditOccasionCommand editOccasionCommand =
                new EditOccasionCommand(INDEX_FIRST_OCCASION, new OccasionDescriptor());
        Occasion editedOccasion = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());

        String expectedMessage = String.format(EditOccasionCommand.MESSAGE_EDIT_OCCASION_SUCCESS, editedOccasion);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editOccasionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showOccasionAtIndex(model, INDEX_FIRST_OCCASION);

        Occasion personInFilteredList = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        Occasion editedOccasion = new OccasionBuilder(personInFilteredList)
                .withOccasionName(VALID_OCCASIONNAME_TWO).build();
        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(INDEX_FIRST_OCCASION,
                new OccasionDescriptorBuilder().withOccasionName(VALID_OCCASIONNAME_TWO).build());

        String expectedMessage = String.format(EditOccasionCommand.MESSAGE_EDIT_OCCASION_SUCCESS, editedOccasion);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateOccasion(model.getFilteredOccasionList().get(0), editedOccasion);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editOccasionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOccasionUnfilteredList_failure() {
        Occasion firstOccasion = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        OccasionDescriptor descriptor = new OccasionDescriptorBuilder(firstOccasion).build();
        EditOccasionCommand editOccasionCommand =
                new EditOccasionCommand(INDEX_SECOND_OCCASION, descriptor);

        assertCommandFailure(editOccasionCommand, model, commandHistory,
                EditOccasionCommand.MESSAGE_DUPLICATE_OCCASION);
    }

    @Test
    public void execute_duplicateOccasionFilteredList_failure() {
        showOccasionAtIndex(model, INDEX_FIRST_OCCASION);

        // edit person in filtered list into a duplicate in address book
        Occasion occasionInList = model.getAddressBook().getOccasionList()
                .get(INDEX_SECOND_OCCASION.getZeroBased());
        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(INDEX_FIRST_OCCASION,
                new OccasionDescriptorBuilder(occasionInList).build());

        assertCommandFailure(editOccasionCommand, model, commandHistory,
                EditOccasionCommand.MESSAGE_DUPLICATE_OCCASION);
    }

    @Test
    public void execute_invalidOccasionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOccasionList().size() + 1);
        OccasionDescriptor descriptor = new OccasionDescriptorBuilder()
                .withOccasionName(VALID_OCCASIONNAME_TWO).build();
        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editOccasionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidOccasionIndexFilteredList_failure() {
        showOccasionAtIndex(model, INDEX_FIRST_OCCASION);
        Index outOfBoundIndex = INDEX_SECOND_OCCASION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOccasionList().size());

        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(outOfBoundIndex,
                new OccasionDescriptorBuilder().withOccasionName(VALID_OCCASIONNAME_TWO).build());

        assertCommandFailure(editOccasionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Occasion editedOccasion = new OccasionBuilder().build();
        Occasion personToEdit = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        OccasionDescriptor descriptor = new OccasionDescriptorBuilder(editedOccasion).build();
        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(INDEX_FIRST_OCCASION, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateOccasion(personToEdit, editedOccasion);
        expectedModel.commitAddressBook();

        // edit -> first person edited
        editOccasionCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOccasionList().size() + 1);
        OccasionDescriptor descriptor = new OccasionDescriptorBuilder()
                .withOccasionName(VALID_OCCASIONNAME_TWO).build();
        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editOccasionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Occasion} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameOccasionEdited() throws Exception {
        Occasion editedOccasion = new OccasionBuilder().build();
        OccasionDescriptor descriptor = new OccasionDescriptorBuilder(editedOccasion).build();
        EditOccasionCommand editOccasionCommand = new EditOccasionCommand(INDEX_FIRST_OCCASION, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showOccasionAtIndex(model, INDEX_SECOND_OCCASION);
        Occasion personToEdit = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        expectedModel.updateOccasion(personToEdit, editedOccasion);
        expectedModel.commitAddressBook();

        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editOccasionCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased()), personToEdit);
        // redo -> edits same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditOccasionCommand standardCommand = new EditOccasionCommand(INDEX_FIRST_OCCASION, DESC_ONE);

        // same values -> returns true
        OccasionDescriptor copyDescriptor = new OccasionDescriptor(DESC_ONE);
        EditOccasionCommand commandWithSameValues = new EditOccasionCommand(INDEX_FIRST_OCCASION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditOccasionCommand(INDEX_SECOND_OCCASION, DESC_ONE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditOccasionCommand(INDEX_FIRST_OCCASION, DESC_TWO)));
    }

}

