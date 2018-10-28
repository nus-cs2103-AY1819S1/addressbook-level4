//TODO : delete

package seedu.souschef.logic.commands;

import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.recipe.Recipe;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */

public class DeleteCommandTest {

    private Model<Recipe> model = new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
    private History history = new History();

    /*@Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToDelete = model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECIPE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RECIPE_SUCCESS, recipeToDelete);

        Model<Recipe> expectedModel = new ModelSetCoordinator(model.getAppContent(), new UserPrefs()).getRecipeModel();
        expectedModel.delete(recipeToDelete);
        expectedModel.commitAppContent();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }*/

    /*@Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }*/

    /*@Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_RECIPE);

        Recipe recipeToDelete = model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECIPE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RECIPE_SUCCESS, recipeToDelete);

        Model<Recipe> expectedModel = new ModelSetCoordinator(model.getAppContent(), new UserPrefs()).getRecipeModel();
        expectedModel.delete(recipeToDelete);
        expectedModel.commitAppContent();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }*/

    /*@Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_RECIPE);

        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppContent().getObservableRecipeList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }*/

    /*@Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Recipe recipeToDelete = model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECIPE);
        Model<Recipe> expectedModel = new ModelSetCoordinator(model.getAppContent(), new UserPrefs()).getRecipeModel();
        expectedModel.delete(recipeToDelete);
        expectedModel.commitAppContent();

        // delete -> first recipe deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered recipe list to show all persons
        expectedModel.undoAppContent();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first recipe deleted again
        expectedModel.redoAppContent();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    /*@Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into recipeModel
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        // single address book state in recipeModel -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }*/

    /**
     * 1. Deletes a {@code Recipe} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted recipe in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the recipe object regardless of indexing.
     **/
    /*@Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECIPE);
        Model<Recipe> expectedModel = new ModelSetCoordinator(model.getAppContent(), new UserPrefs()).getRecipeModel();

        showPersonAtIndex(model, INDEX_SECOND_RECIPE);
        Recipe recipeToDelete = model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased());
        expectedModel.delete(recipeToDelete);
        expectedModel.commitAppContent();

        // delete -> deletes second recipe in unfiltered recipe list / first recipe in filtered recipe list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered recipe list to show all persons
        expectedModel.undoAppContent();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(recipeToDelete, model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased()));
        // redo -> deletes same second recipe in unfiltered recipe list
        expectedModel.redoAppContent();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    /*@Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_RECIPE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_RECIPE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_RECIPE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }*/

    /**
     * Updates {@code recipeModel}'s filtered list to show no one.
     **/
    /*private void showNoPerson(Model model) {
        model.updateFilteredList(p -> false);

        assertTrue(model.getFilteredList().isEmpty());
    }*/
}
