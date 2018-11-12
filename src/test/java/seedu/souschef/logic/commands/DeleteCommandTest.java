//TODO : delete

package seedu.souschef.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.souschef.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.souschef.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.souschef.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

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

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recipe recipeToDelete = model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(model, recipeToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, "recipe", recipeToDelete);

        Model<Recipe> expectedModel = new ModelSetCoordinator(model.getAppContent(), new UserPrefs()).getRecipeModel();
        expectedModel.delete(recipeToDelete);
        expectedModel.commitAppContent();

        assertCommandSuccess(deleteCommand, model, history, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_RECIPE);

        Recipe recipeToDelete = model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(model, recipeToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SUCCESS, "recipe", recipeToDelete);

        Model<Recipe> expectedModel = new ModelSetCoordinator(model.getAppContent(), new UserPrefs()).getRecipeModel();
        expectedModel.delete(recipeToDelete);
        expectedModel.commitAppContent();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, history, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Recipe recipeToDelete1 = model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased());
        DeleteCommand deleteFirstCommand = new DeleteCommand(model, recipeToDelete1);
        Recipe recipeToDelete2 = model.getFilteredList().get(INDEX_SECOND_RECIPE.getZeroBased());
        DeleteCommand deleteSecondCommand = new DeleteCommand(model, recipeToDelete2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(model, recipeToDelete1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code recipeModel}'s filtered list to show no one.
     **/
    private void showNoPerson(Model model) {
        model.updateFilteredList(p -> false);

        assertTrue(model.getFilteredList().isEmpty());
    }
}
