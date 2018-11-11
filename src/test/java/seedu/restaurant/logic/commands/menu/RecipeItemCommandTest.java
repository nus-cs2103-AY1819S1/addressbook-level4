package seedu.restaurant.logic.commands.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_RECIPE_FRIES;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.menu.MenuCommandTestUtil.showItemAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Recipe;
import seedu.restaurant.testutil.menu.ItemBuilder;

//@@author yican95
/**
 * Contains integration tests (interaction with the Model) and unit tests for RecipeItemCommand.
 */
public class RecipeItemCommandTest {

    private static final String RECIPE_STUB = "Some recipe";

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deleteRecipeUnfilteredList_success() {
        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(firstItem).withRecipe("").build();

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(INDEX_FIRST,
                new Recipe(editedItem.getRecipe().toString()));

        String expectedMessage = String.format(RecipeItemCommand.MESSAGE_DELETE_RECIPE_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateItem(firstItem, editedItem);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(recipeItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST);

        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(model.getFilteredItemList().get(INDEX_FIRST.getZeroBased()))
                .withRecipe(RECIPE_STUB).build();

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(INDEX_FIRST,
                new Recipe(editedItem.getRecipe().toString()));

        String expectedMessage = String.format(RecipeItemCommand.MESSAGE_ADD_RECIPE_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateItem(firstItem, editedItem);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(recipeItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(outOfBoundIndex,
                new Recipe(VALID_ITEM_RECIPE_FRIES));

        assertCommandFailure(recipeItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of menu
     */
    @Test
    public void execute_invalidItemIndexFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;

        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getItemList().size());

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(outOfBoundIndex,
                new Recipe(VALID_ITEM_RECIPE_FRIES));
        assertCommandFailure(recipeItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item itemToModify = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item modifiedItem = new ItemBuilder(itemToModify).withRecipe(RECIPE_STUB).build();

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(INDEX_FIRST, new Recipe(RECIPE_STUB));

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.updateItem(itemToModify, modifiedItem);
        expectedModel.commitRestaurantBook();

        // recipe -> first item recipe changed
        recipeItemCommand.execute(model, commandHistory);

        // undo -> reverts restaurant book back to previous state and filtered item list to show all items
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item modified again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(outOfBoundIndex, new Recipe(""));

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(recipeItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() {
        final RecipeItemCommand standardCommand = new RecipeItemCommand(INDEX_FIRST,
                new Recipe(VALID_ITEM_RECIPE_FRIES));

        // same values -> returns true
        RecipeItemCommand commandWithSameValues = new RecipeItemCommand(INDEX_FIRST,
                new Recipe(VALID_ITEM_RECIPE_FRIES));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMenuCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RecipeItemCommand(INDEX_SECOND, new Recipe(VALID_ITEM_RECIPE_FRIES))));

        // different recipe -> returns false
        assertFalse(standardCommand.equals(new RecipeItemCommand(INDEX_FIRST, new Recipe("Other recipe"))));
    }
}
