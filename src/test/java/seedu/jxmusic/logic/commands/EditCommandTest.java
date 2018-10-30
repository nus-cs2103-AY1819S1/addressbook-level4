//package seedu.jxmusic.logic.commands;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.DESC_AMY;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.DESC_BOB;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_NAME_METAL;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_IHOJIN;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.jxmusic.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalAddressBook;
//
//import org.junit.Test;
//
//import seedu.jxmusic.commons.core.Messages;
//import seedu.jxmusic.commons.core.index.Index;
//import seedu.jxmusic.logic.commands.EditCommand.EditPersonDescriptor;
//import seedu.jxmusic.model.AddressBook;
//import seedu.jxmusic.model.Model;
//import seedu.jxmusic.model.ModelManager;
//import seedu.jxmusic.model.UserPrefs;
//import seedu.jxmusic.testutil.EditPersonDescriptorBuilder;
//import seedu.jxmusic.testutil.PlaylistBuilder;
//
///**
// * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand
// */
//public class EditCommandTest {
//
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//    private CommandHistory commandHistory = new CommandHistory();
//
//    @Test
//    public void execute_allFieldsSpecifiedUnfilteredList_success() {
//        Person editedPerson = new PlaylistBuilder().build();
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_someFieldsSpecifiedUnfilteredList_success() {
//        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
//        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
//
//        PlaylistBuilder personInList = new PlaylistBuilder(lastPerson);
//        Person editedPerson = personInList.withName(VALID_NAME_METAL).withPhone(VALID_PHONE_BOB)
//                .withTags(VALID_TRACK_NAME_IHOJIN).build();
//
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_METAL)
//                .withPhone(VALID_PHONE_BOB).withTags(VALID_TRACK_NAME_IHOJIN).build();
//        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.updatePerson(lastPerson, editedPerson);
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_noFieldSpecifiedUnfilteredList_success() {
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
//        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_filteredList_success() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//
//        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        Person editedPerson = new PlaylistBuilder(personInFilteredList).withName(VALID_NAME_METAL).build();
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
//                new EditPersonDescriptorBuilder().withName(VALID_NAME_METAL).build());
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
//        expectedModel.commitAddressBook();
//
//        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_duplicatePersonUnfilteredList_failure() {
//        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);
//
//        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
//    }
//
//    @Test
//    public void execute_duplicatePersonFilteredList_failure() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//
//        // edit playlist in filtered list into a duplicate in jxmusic book
//        Person personInList = model.getAddressBook().getPlaylistList().get(INDEX_SECOND_PERSON.getZeroBased());
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
//                new EditPersonDescriptorBuilder(personInList).build());
//
//        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
//    }
//
//    @Test
//    public void execute_invalidPersonIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_METAL).build();
//        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
//
//        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    /**
//     * Edit filtered list where index is larger than size of filtered list,
//     * but smaller than size of jxmusic book
//     */
//    @Test
//    public void execute_invalidPersonIndexFilteredList_failure() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//        Index outOfBoundIndex = INDEX_SECOND_PERSON;
//        // ensures that outOfBoundIndex is still in bounds of jxmusic book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPlaylistList().size());
//
//        EditCommand editCommand = new EditCommand(outOfBoundIndex,
//                new EditPersonDescriptorBuilder().withName(VALID_NAME_METAL).build());
//
//        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
//        Person editedPerson = new PlaylistBuilder().build();
//        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//        expectedModel.updatePerson(personToEdit, editedPerson);
//        expectedModel.commitAddressBook();
//
//        // edit -> first playlist edited
//        editCommand.execute(model, commandHistory);
//
//        // undo -> reverts addressbook back to previous state and filtered playlist list to show all persons
//        expectedModel.undoAddressBook();
//        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        // redo -> same first playlist edited again
//        expectedModel.redoAddressBook();
//        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_METAL).build();
//        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
//
//        // execution failed -> jxmusic book state not added into model
//        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//
//        // single jxmusic book state in model -> undoCommand and redoCommand fail
//        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
//        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
//    }
//
//    /**
//     * 1. Edits a {@code Person} from a filtered list.
//     * 2. Undo the edit.
//     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited playlist in the
//     * unfiltered list is different from the index at the filtered list.
//     * 4. Redo the edit. This ensures {@code RedoCommand} edits the playlist object regardless of indexing.
//     */
//    @Test
//    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
//        Person editedPerson = new PlaylistBuilder().build();
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
//        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
//
//        showPersonAtIndex(model, INDEX_SECOND_PERSON);
//        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        expectedModel.updatePerson(personToEdit, editedPerson);
//        expectedModel.commitAddressBook();
//
//        // edit -> edits second playlist in unfiltered playlist list / first playlist in filtered playlist list
//        editCommand.execute(model, commandHistory);
//
//        // undo -> reverts addressbook back to previous state and filtered playlist list to show all persons
//        expectedModel.undoAddressBook();
//        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
//        // redo -> edits same second playlist in unfiltered playlist list
//        expectedModel.redoAddressBook();
//        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void equals() {
//        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);
//
//        // same values -> returns true
//        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
//        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
//        assertTrue(standardCommand.equals(commandWithSameValues));
//
//        // same object -> returns true
//        assertTrue(standardCommand.equals(standardCommand));
//
//        // null -> returns false
//        assertFalse(standardCommand.equals(null));
//
//        // different types -> returns false
//        assertFalse(standardCommand.equals(new ClearCommand()));
//
//        // different index -> returns false
//        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));
//
//        // different descriptor -> returns false
//        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
//    }
//
//}
