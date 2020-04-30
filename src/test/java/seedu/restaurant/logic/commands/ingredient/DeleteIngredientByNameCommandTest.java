package seedu.restaurant.logic.commands.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;

//@@author rebstan97
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteIngredientByNameCommand}.
 */
public class DeleteIngredientByNameCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validName_success() {
        Ingredient ingredientToDelete = model.findIngredient(new IngredientName("Chinese Cabbage"));
        DeleteIngredientByNameCommand deleteCommand = new DeleteIngredientByNameCommand(new IngredientName("Chinese "
                + "Cabbage"));

        String expectedMessage = String.format(DeleteIngredientByNameCommand.MESSAGE_DELETE_INGREDIENT_SUCCESS,
                ingredientToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteIngredient(ingredientToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nameNotFound_throwsCommandException() {
        IngredientName nameNotFound = new IngredientName("Chicken Thigh");
        DeleteIngredientByNameCommand deleteCommand = new DeleteIngredientByNameCommand(nameNotFound);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_validName_success() throws Exception {
        Ingredient ingredientToDelete = model.findIngredient(new IngredientName("Chinese Cabbage"));
        DeleteIngredientByNameCommand deleteCommand = new DeleteIngredientByNameCommand(new IngredientName("Chinese "
                + "Cabbage"));
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteIngredient(ingredientToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first ingredient deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered ingredient list to show all ingredients
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first ingredient deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidName_failure() {
        IngredientName nameNotFound = new IngredientName("Chicken Thigh");
        DeleteIngredientByNameCommand deleteCommand = new DeleteIngredientByNameCommand(nameNotFound);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteIngredientByNameCommand deleteFirstCommand = new DeleteIngredientByNameCommand(new IngredientName(
                "Chinese Cabbage"));
        DeleteIngredientByNameCommand deleteSecondCommand = new DeleteIngredientByNameCommand(new IngredientName(
                "Mexican Avocado"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteIngredientByNameCommand deleteFirstCommandCopy = new DeleteIngredientByNameCommand(new IngredientName(
                "Chinese Cabbage"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different ingredient -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
