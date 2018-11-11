package seedu.restaurant.logic.commands.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.DESC_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.DESC_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_MINIMUM_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_UNIT_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.CommandTestUtil.showIngredientAtIndex;
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
import seedu.restaurant.testutil.ingredient.EditIngredientDescriptorBuilder;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;

//@@author rebstan97
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditIngredientByIndexCommand.
 */
public class EditIngredientByIndexCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Ingredient editedIngredient = new IngredientBuilder().build();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(editedIngredient).build();
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditIngredientByIndexCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS,
                editedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateIngredient(model.getFilteredIngredientList().get(0), editedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIngredient = Index.fromOneBased(model.getFilteredIngredientList().size());
        Ingredient lastIngredient = model.getFilteredIngredientList().get(indexLastIngredient.getZeroBased());

        IngredientBuilder ingredientInList = new IngredientBuilder(lastIngredient);
        Ingredient editedIngredient = ingredientInList.withName(VALID_NAME_BROCCOLI).withUnit(VALID_UNIT_BROCCOLI)
                .withMinimum(VALID_MINIMUM_BROCCOLI).build();

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BROCCOLI)
                .withUnit(VALID_UNIT_BROCCOLI).withMinimum(VALID_MINIMUM_BROCCOLI).build();
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(indexLastIngredient, descriptor);

        String expectedMessage = String.format(EditIngredientByIndexCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS,
                editedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateIngredient(lastIngredient, editedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(INDEX_FIRST,
                new EditIngredientDescriptor());
        Ingredient editedIngredient = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditIngredientByIndexCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS,
                editedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showIngredientAtIndex(model, INDEX_FIRST);

        Ingredient ingredientInFilteredList = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        Ingredient editedIngredient = new IngredientBuilder(ingredientInFilteredList)
                .withName(VALID_NAME_BROCCOLI).build();
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(INDEX_FIRST,
                new EditIngredientDescriptorBuilder().withName(VALID_NAME_BROCCOLI).build());

        String expectedMessage = String.format(EditIngredientByIndexCommand.MESSAGE_EDIT_INGREDIENT_SUCCESS,
                editedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateIngredient(model.getFilteredIngredientList().get(0), editedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIngredientUnfilteredList_failure() {
        Ingredient firstIngredient = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(firstIngredient).build();
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, commandHistory,
                EditIngredientByIndexCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

    @Test
    public void execute_duplicateIngredientFilteredList_failure() {
        showIngredientAtIndex(model, INDEX_FIRST);

        // edit ingredient in filtered list into a duplicate in restaurant book
        Ingredient ingredientInList = model.getRestaurantBook().getIngredientList().get(INDEX_SECOND.getZeroBased());
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(INDEX_FIRST,
                new EditIngredientDescriptorBuilder(ingredientInList).build());

        assertCommandFailure(editCommand, model, commandHistory,
                EditIngredientByIndexCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

    @Test
    public void execute_invalidIngredientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder()
                .withName(VALID_NAME_BROCCOLI).build();
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of restaurant book
     */
    @Test
    public void execute_invalidIngredientIndexFilteredList_failure() {
        showIngredientAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getIngredientList().size());

        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(outOfBoundIndex,
                new EditIngredientDescriptorBuilder().withName(VALID_NAME_BROCCOLI).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Ingredient editedIngredient = new IngredientBuilder().build();
        Ingredient ingredientToEdit = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(editedIngredient).build();
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(INDEX_FIRST, descriptor);
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
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder()
                .withName(VALID_NAME_BROCCOLI).build();
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(outOfBoundIndex, descriptor);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Ingredient} from a filtered list. 2. Undo the edit. 3. The unfiltered list should be shown now.
     * Verify that the index of the previously edited ingredient in the unfiltered list is different from the index at
     * the filtered list. 4. Redo the edit. This ensures {@code RedoCommand} edits the ingredient object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameIngredientEdited() throws Exception {
        Ingredient editedIngredient = new IngredientBuilder().build();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(editedIngredient).build();
        EditIngredientByIndexCommand editCommand = new EditIngredientByIndexCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());

        showIngredientAtIndex(model, INDEX_SECOND);
        Ingredient ingredientToEdit = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateIngredient(ingredientToEdit, editedIngredient);
        expectedModel.commitRestaurantBook();

        // edit -> edits second ingredient in unfiltered ingredient list / first ingredient in filtered ingredient list
        editCommand.execute(model, commandHistory);

        // undo -> reverts restaurant back to previous state and filtered ingredient list to show all ingredients
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased()), ingredientToEdit);
        // redo -> edits same second ingredient in unfiltered ingredient list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditIngredientByIndexCommand standardCommand = new EditIngredientByIndexCommand(INDEX_FIRST, DESC_APPLE);

        // same values -> returns true
        EditIngredientDescriptor copyDescriptor = new EditIngredientDescriptor(DESC_APPLE);
        EditIngredientByIndexCommand commandWithSameValues = new EditIngredientByIndexCommand(
                INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListIngredientsCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditIngredientByIndexCommand(INDEX_SECOND, DESC_APPLE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditIngredientByIndexCommand(INDEX_FIRST, DESC_BROCCOLI)));
    }

}
