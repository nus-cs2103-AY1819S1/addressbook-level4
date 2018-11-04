package seedu.restaurant.logic.commands.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.menu.MenuCommandTestUtil.showItemAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import java.util.HashMap;
import java.util.Map;

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
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.testutil.menu.ItemBuilder;

//@@author yican95
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddRequiredIngredientsCommand.
 */
public class AddRequiredIngredientsCommandTest {

    private static final Map<IngredientName, Integer> REQUIRED_INGREDIENTS_STUB = new HashMap<>();

    static {
        REQUIRED_INGREDIENTS_STUB.put(new IngredientName("Apple"), 3);
    }

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deleteRequiredIngredientsUnfilteredList_success() {
        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(firstItem).withRequiredIngredients(new HashMap<>()).build();

        AddRequiredIngredientsCommand addRequiredIngredientsCommand =
                new AddRequiredIngredientsCommand(INDEX_FIRST, editedItem.getRequiredIngredients());

        String expectedMessage =
                String.format(AddRequiredIngredientsCommand.MESSAGE_DELETE_REQUIRED_INGREDIENT_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateItem(firstItem, editedItem);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(addRequiredIngredientsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST);

        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(model.getFilteredItemList().get(INDEX_FIRST.getZeroBased()))
                .withRequiredIngredients(Map.of("Apple", "3")).build();

        AddRequiredIngredientsCommand addRequiredIngredientsCommand = new AddRequiredIngredientsCommand(INDEX_FIRST,
                editedItem.getRequiredIngredients());

        String expectedMessage = String.format(AddRequiredIngredientsCommand.MESSAGE_ADD_REQUIRED_INGREDIENT_SUCCESS,
                editedItem);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateItem(firstItem, editedItem);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(addRequiredIngredientsCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        AddRequiredIngredientsCommand addRequiredIngredientsCommand =
                new AddRequiredIngredientsCommand(outOfBoundIndex, REQUIRED_INGREDIENTS_STUB);

        assertCommandFailure(addRequiredIngredientsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
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

        AddRequiredIngredientsCommand addRequiredIngredientsCommand =
                new AddRequiredIngredientsCommand(outOfBoundIndex, REQUIRED_INGREDIENTS_STUB);
        assertCommandFailure(addRequiredIngredientsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item itemToModify = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item modifiedItem = new ItemBuilder(itemToModify)
                .withRequiredIngredients(Map.of("Apple", "3")).build();

        AddRequiredIngredientsCommand addRequiredIngredientsCommand =
                new AddRequiredIngredientsCommand(INDEX_FIRST, REQUIRED_INGREDIENTS_STUB);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.updateItem(itemToModify, modifiedItem);
        expectedModel.commitRestaurantBook();

        // required ingredients -> first item required ingredients changed
        addRequiredIngredientsCommand.execute(model, commandHistory);

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

        AddRequiredIngredientsCommand addRequiredIngredientsCommand =
                new AddRequiredIngredientsCommand(outOfBoundIndex, new HashMap<>());

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(addRequiredIngredientsCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() {
        final AddRequiredIngredientsCommand standardCommand = new AddRequiredIngredientsCommand(INDEX_FIRST,
                REQUIRED_INGREDIENTS_STUB);

        // same values -> returns true
        AddRequiredIngredientsCommand commandWithSameValues = new AddRequiredIngredientsCommand(INDEX_FIRST,
                REQUIRED_INGREDIENTS_STUB);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMenuCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddRequiredIngredientsCommand(INDEX_SECOND,
                REQUIRED_INGREDIENTS_STUB)));

        // different recipe -> returns false
        assertFalse(standardCommand.equals(new AddRequiredIngredientsCommand(INDEX_FIRST, new HashMap<>())));
    }
}
