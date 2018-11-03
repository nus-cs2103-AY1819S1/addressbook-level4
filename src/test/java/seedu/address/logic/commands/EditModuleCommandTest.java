package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandModuleTestUtil.DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_CALCULUS;
import static seedu.address.logic.commands.CommandModuleTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandModuleTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandModuleTestUtil.showModuleAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalModules.getTypicalModulesAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ModuleDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit
 * tests for EditModuleCommand.
 */
public class EditModuleCommandTest {

    private Model model = new ModelManager(getTypicalModulesAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Module editedModule = new ModuleBuilder().build();
        ModuleDescriptor descriptor = new ModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateModule(model.getFilteredModuleList().get(0), editedModule);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editModuleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastModule = Index.fromOneBased(model.getFilteredModuleList().size());
        Module lastModule = model.getFilteredModuleList().get(indexLastModule.getZeroBased());

        ModuleBuilder personInList = new ModuleBuilder(lastModule);
        Module editedModule = personInList.withModuleTitle(VALID_MODULETITLE_ST2131)
                .withModuleCode(VALID_MODULECODE_ST2131).withTags(VALID_TAG_CALCULUS).build();

        ModuleDescriptor descriptor = new ModuleDescriptorBuilder().withModuleTitle(VALID_MODULETITLE_ST2131)
                .withModuleCode(VALID_MODULECODE_ST2131).withTags(VALID_TAG_CALCULUS).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(indexLastModule, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateModule(lastModule, editedModule);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editModuleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE, new ModuleDescriptor());
        Module editedModule = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editModuleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Module moduleInFilteredList = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleInFilteredList).withModuleTitle(VALID_MODULETITLE_CS2100).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE,
                new ModuleDescriptorBuilder().withModuleTitle(VALID_MODULETITLE_CS2100).build());

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateModule(model.getFilteredModuleList().get(0), editedModule);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editModuleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateModuleUnfilteredList_failure() {
        Module firstModule = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        ModuleDescriptor descriptor = new ModuleDescriptorBuilder(firstModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_SECOND_MODULE, descriptor);

        assertCommandFailure(editModuleCommand, model, commandHistory, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_duplicateModuleFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        // edit person in filtered list into a duplicate in address book
        Module personInList = model.getAddressBook().getModuleList().get(INDEX_SECOND_MODULE.getZeroBased());
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE,
                new ModuleDescriptorBuilder(personInList).build());

        assertCommandFailure(editModuleCommand, model, commandHistory, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_invalidModuleIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        ModuleDescriptor descriptor = new ModuleDescriptorBuilder()
                .withModuleTitle(VALID_MODULETITLE_ST2131).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editModuleCommand, model, commandHistory, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidModuleIndexFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);
        Index outOfBoundIndex = INDEX_SECOND_MODULE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getModuleList().size());

        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex,
                new ModuleDescriptorBuilder().withModuleTitle(VALID_MODULETITLE_ST2131).build());

        assertCommandFailure(editModuleCommand, model, commandHistory, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Module editedModule = new ModuleBuilder().build();
        Module personToEdit = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        ModuleDescriptor descriptor = new ModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateModule(personToEdit, editedModule);
        expectedModel.commitAddressBook();

        // edit -> first person edited
        editModuleCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        ModuleDescriptor descriptor = new ModuleDescriptorBuilder()
                .withModuleTitle(VALID_MODULETITLE_ST2131).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editModuleCommand, model, commandHistory, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Module} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameModuleEdited() throws Exception {
        Module editedModule = new ModuleBuilder().build();
        ModuleDescriptor descriptor = new ModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showModuleAtIndex(model, INDEX_SECOND_MODULE);
        Module personToEdit = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        expectedModel.updateModule(personToEdit, editedModule);
        expectedModel.commitAddressBook();

        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editModuleCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased()), personToEdit);
        // redo -> edits same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditModuleCommand standardCommand = new EditModuleCommand(INDEX_FIRST_MODULE, DESC_CS2100);

        // same values -> returns true
        ModuleDescriptor copyDescriptor = new ModuleDescriptor(DESC_CS2100);
        EditModuleCommand commandWithSameValues = new EditModuleCommand(INDEX_FIRST_MODULE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(INDEX_SECOND_MODULE, DESC_CS2100)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(INDEX_FIRST_MODULE, DESC_ST2131)));
    }

}
