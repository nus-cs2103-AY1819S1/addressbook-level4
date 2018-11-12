package seedu.restaurant.logic.commands.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.DESC_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.DESC_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_MINIMUM_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_UNIT_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.RedoCommand;
import seedu.restaurant.logic.commands.UndoCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand.EditIngredientDescriptor;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.testutil.ingredient.EditIngredientDescriptorBuilder;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;

//@@author rebstan97
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditIngredientByNameCommand.
 */
public class EditIngredientByNameCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecified_success() {
        Ingredient editedIngredient = new IngredientBuilder().build();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(editedIngredient).build();
        IngredientName ingredientToEdit = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased()).getName();
        EditIngredientByNameCommand editCommand = new EditIngredientByNameCommand(ingredientToEdit, descriptor);

        String expectedMessage = String.format(EditIngredientByNameCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS,
                editedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateIngredient(model.findIngredient(ingredientToEdit), editedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Index indexLastIngredient = Index.fromOneBased(model.getFilteredIngredientList().size());
        Ingredient lastIngredient = model.getFilteredIngredientList().get(indexLastIngredient.getZeroBased());

        IngredientBuilder ingredientInList = new IngredientBuilder(lastIngredient);
        Ingredient editedIngredient = ingredientInList.withName(VALID_NAME_BROCCOLI).withUnit(VALID_UNIT_BROCCOLI)
                .withMinimum(VALID_MINIMUM_BROCCOLI).build();

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BROCCOLI)
                .withUnit(VALID_UNIT_BROCCOLI).withMinimum(VALID_MINIMUM_BROCCOLI).build();
        EditIngredientByNameCommand editCommand = new EditIngredientByNameCommand(lastIngredient.getName(), descriptor);

        String expectedMessage = String.format(EditIngredientByNameCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS,
                editedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateIngredient(lastIngredient, editedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        IngredientName ingredientToEdit = model.getFilteredIngredientList().get(INDEX_SECOND.getZeroBased()).getName();
        EditIngredientByNameCommand editCommand = new EditIngredientByNameCommand(ingredientToEdit,
                new EditIngredientDescriptor());
        Ingredient editedIngredient = model.findIngredient(ingredientToEdit);

        String expectedMessage = String.format(EditIngredientByNameCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS,
                editedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }



    @Test
    public void execute_duplicateIngredient_failure() {
        Ingredient firstIngredient = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        IngredientName ingredientToEdit = model.getFilteredIngredientList().get(INDEX_SECOND.getZeroBased()).getName();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(firstIngredient).build();
        EditIngredientByNameCommand editCommand = new EditIngredientByNameCommand(
                ingredientToEdit, descriptor);

        assertCommandFailure(editCommand, model, commandHistory,
                EditIngredientByNameCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }


    @Test
    public void execute_invalidIngredientName_failure() {
        IngredientName invalidName = new IngredientName("Chicken Thigh");
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder()
                .withName(VALID_NAME_BROCCOLI).build();
        EditIngredientByNameCommand editCommand = new EditIngredientByNameCommand(invalidName, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_validName_success() throws Exception {
        Ingredient editedIngredient = new IngredientBuilder().build();
        Ingredient ingredientToEdit = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(editedIngredient).build();
        EditIngredientByNameCommand editCommand = new EditIngredientByNameCommand(
                ingredientToEdit.getName(), descriptor);
        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateIngredient(ingredientToEdit, editedIngredient);
        expectedModel.commitRestaurantBook();

        // edit -> first ingredient edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts restaurant back to previous state and filtered ingredient list to show all ingredients
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first ingredient edited again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidName_failure() {
        IngredientName invalidName = new IngredientName("Chicken Thigh");
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder()
                .withName(VALID_NAME_BROCCOLI).build();
        EditIngredientByNameCommand editCommand = new EditIngredientByNameCommand(invalidName, descriptor);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final EditIngredientByNameCommand standardCommand = new EditIngredientByNameCommand(
                new IngredientName(VALID_NAME_APPLE), DESC_BROCCOLI);

        // same values -> returns true
        EditIngredientDescriptor copyDescriptor = new EditIngredientDescriptor(DESC_BROCCOLI);
        EditIngredientByNameCommand commandWithSameValues = new EditIngredientByNameCommand(
                new IngredientName(VALID_NAME_APPLE), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListIngredientsCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditIngredientByNameCommand(new IngredientName(VALID_NAME_APPLE),
                DESC_APPLE)));

        // different name -> returns false
        assertFalse(standardCommand.equals(new EditIngredientByNameCommand(new IngredientName(VALID_NAME_BROCCOLI),
                DESC_BROCCOLI)));
    }

}
