package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.TypeUtil.MODULE;
import static seedu.address.commons.util.TypeUtil.OCCASION;
import static seedu.address.commons.util.TypeUtil.PERSON;
import static seedu.address.logic.commands.CommandModuleTestUtil.showModuleAtIndex;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONNAME_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.showOccasionAtIndex;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandPersonTestUtil.showPersonAtIndex;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONINDEX;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OCCASION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OCCASION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalModules.CS2100;
import static seedu.address.testutil.TypicalModules.ST2131;
import static seedu.address.testutil.TypicalOccasions.OCCASION_ONE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model, InsertPerson, UndoCommand and RedoCommand)
 * and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredPersonList_success() {
        model.setActiveType(PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredModuleList_success() {
        model.setActiveType(MODULE);
        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MODULE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredOccasionList_success() {
        model.setActiveType(OCCASION);
        Occasion occasionToDelete = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_OCCASION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_OCCASION_SUCCESS, occasionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOccasion(occasionToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredPersonList_throwsCommandException() {
        model.setActiveType(PERSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredModuleList_throwsCommandException() {
        model.setActiveType(MODULE);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredOccasionList_throwsCommandException() {
        model.setActiveType(OCCASION);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOccasionList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredPersonList_success() {
        model.setActiveType(PERSON);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredModuleList_success() {
        model.setActiveType(MODULE);
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MODULE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        expectedModel.commitAddressBook();
        showNoModule(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredOccasionList_success() {
        model.setActiveType(OCCASION);
        showOccasionAtIndex(model, INDEX_FIRST_OCCASION);

        Occasion occasionToDelete = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_OCCASION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_OCCASION_SUCCESS, occasionToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOccasion(occasionToDelete);
        expectedModel.commitAddressBook();
        showNoOccasion(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredPersonList_throwsCommandException() {
        model.setActiveType(PERSON);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredModuleList_throwsCommandException() {
        model.setActiveType(MODULE);
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Index outOfBoundIndex = INDEX_SECOND_MODULE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getModuleList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredOccasionList_throwsCommandException() {
        model.setActiveType(OCCASION);
        showOccasionAtIndex(model, INDEX_FIRST_OCCASION);

        Index outOfBoundIndex = INDEX_SECOND_OCCASION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOccasionList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredPersonList_success() throws Exception {
        model.setActiveType(PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        // delete -> first person deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredModuleList_success() throws Exception {
        model.setActiveType(MODULE);
        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MODULE);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        expectedModel.commitAddressBook();

        // delete -> first module deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered module list to show all modules
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first module deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredOccasionList_success() throws Exception {
        model.setActiveType(OCCASION);
        Occasion occasionToDelete = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_OCCASION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOccasion(occasionToDelete);
        expectedModel.commitAddressBook();

        // delete -> first occasion deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered occasion list to show all occasions
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first occasion deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredPersonList_failure() {
        model.setActiveType(PERSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredModuleList_failure() {
        model.setActiveType(MODULE);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredOccasionList_failure() {
        model.setActiveType(OCCASION);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOccasionList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        model.setActiveType(PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * 1. Deletes a {@code Module} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted module in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the module object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameModuleDeleted() throws Exception {
        model.setActiveType(MODULE);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MODULE);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showModuleAtIndex(model, INDEX_SECOND_MODULE);
        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        expectedModel.deleteModule(moduleToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second module in unfiltered module list / first module in filtered module list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered module list to show all modules
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(moduleToDelete, model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased()));
        // redo -> deletes same second module in unfiltered module list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * 1. Deletes a {@code Occasion} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted occasion in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the occasion object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameOccasionDeleted() throws Exception {
        model.setActiveType(OCCASION);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_OCCASION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showOccasionAtIndex(model, INDEX_SECOND_OCCASION);
        Occasion occasionToDelete = model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased());
        expectedModel.deleteOccasion(occasionToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second occasion in unfiltered occasion list / first occasion in filtered occasion list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered occasion list to show all occasions
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(occasionToDelete, model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased()));
        // redo -> deletes same second occasion in unfiltered occasion list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeInsertPerson_validIndexFilteredList_personRemovedFromModule() throws Exception {
        model.setActiveType(PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        InsertPersonCommand insertPersonIntoModuleCommand = new InsertPersonCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_MODULE, ST2131); //dummy module used as indicator of insert 'type'
        insertPersonIntoModuleCommand.execute(model, commandHistory);
        deleteCommand.execute(model, commandHistory);
        assertEquals(expectedModel.getFilteredModuleList().get(INDEX_FIRST_MODULE.getOneBased()).getStudents(),
                model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getOneBased()).getStudents());
    }

    @Test
    public void executeInsertPerson_validIndexFilteredList_personRemovedFromOccasion() throws Exception {
        model.setActiveType(PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        InsertPersonCommand insertPersonIntoOccasionCommand = new InsertPersonCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_OCCASION, OCCASION_ONE); //dummy occasion used as indicator of insert 'type'
        insertPersonIntoOccasionCommand.execute(model, commandHistory);
        deleteCommand.execute(model, commandHistory);
        assertEquals(expectedModel.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getOneBased()).getAttendanceList(),
                model.getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getOneBased()).getAttendanceList());
    }

    @Test
    public void executeInsertPerson_validIndexFilteredList_moduleRemovedFromPerson() throws Exception {
        model.setActiveType(MODULE);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MODULE);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        InsertPersonCommand insertPersonIntoModuleCommand = new InsertPersonCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_MODULE, ST2131); //dummy module used as indicator of insert 'type'
        insertPersonIntoModuleCommand.execute(model, commandHistory);
        deleteCommand.execute(model, commandHistory);
        assertEquals(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getOneBased()).getModuleList(),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getOneBased()).getModuleList());
    }

    @Test
    public void executeInsertPerson_validIndexFilteredList_occasionRemovedFromPerson() throws Exception {
        model.setActiveType(OCCASION);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_OCCASION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        InsertPersonCommand insertPersonIntoOccasionCommand = new InsertPersonCommand(INDEX_FIRST_PERSON,
                INDEX_FIRST_OCCASION, OCCASION_ONE); //dummy occasion used as indicator of insert 'type'
        insertPersonIntoOccasionCommand.execute(model, commandHistory);
        deleteCommand.execute(model, commandHistory);
        assertEquals(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getOneBased()).getOccasionList(),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getOneBased()).getOccasionList());
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstPersonCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondPersonCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstModuleCommand = new DeleteCommand(INDEX_FIRST_MODULE);
        DeleteCommand deleteSecondOccasionCommand = new DeleteCommand(INDEX_SECOND_OCCASION);

        // same object -> returns true
        assertTrue(deleteFirstPersonCommand.equals(deleteFirstPersonCommand));

        // same index on same type -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstPersonCommand.equals(deleteFirstCommandCopy));

        // same index on different types -> returns true
        assertTrue(deleteFirstPersonCommand.equals(deleteFirstModuleCommand));
        assertTrue(deleteSecondPersonCommand.equals(deleteSecondOccasionCommand));

        // comparing to a non-DeleteCommand -> returns false
        assertFalse(deleteFirstPersonCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstPersonCommand.equals(null));

        // different index on same type -> returns false
        assertFalse(deleteFirstPersonCommand.equals(deleteSecondPersonCommand));

        // different index on different values -> returns false
        assertFalse(deleteFirstPersonCommand.equals(deleteSecondOccasionCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoModule(Model model) {
        model.updateFilteredModuleList(p -> false);

        assertTrue(model.getFilteredModuleList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoOccasion(Model model) {
        model.updateFilteredOccasionList(p -> false);

        assertTrue(model.getFilteredOccasionList().isEmpty());
    }
}
