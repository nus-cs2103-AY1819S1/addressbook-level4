package seedu.restaurant.logic.commands.menu;

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
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;

//@@author yican95
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteItemByNameCommand}.
 */
public class DeleteItemByNameCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validName_success() {
        Item itemToDelete = model.findItem(new Name("Apple Juice"));
        DeleteItemByNameCommand deleteItemCommand = new DeleteItemByNameCommand(new Name("Apple Juice"));

        String expectedMessage = String.format(DeleteItemByNameCommand.MESSAGE_DELETE_ITEM_SUCCESS, itemToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nameNotFound_throwsCommandException() {
        Name nameNotFound = new Name("Apple Pie");
        DeleteItemByNameCommand deleteItemCommand = new DeleteItemByNameCommand(nameNotFound);

        assertCommandFailure(deleteItemCommand, model, commandHistory, Messages.MESSAGE_NAME_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_validName_success() throws Exception {
        Item itemToDelete = model.findItem(new Name("Apple Juice"));
        DeleteItemByNameCommand deleteItemCommand = new DeleteItemByNameCommand(new Name("Apple Juice"));
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteItem(itemToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first item deleted
        deleteItemCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered item list to show all item
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidName_failure() {
        Name nameNotFound = new Name("Apple Pie");
        DeleteItemByNameCommand deleteItemCommand = new DeleteItemByNameCommand(nameNotFound);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(deleteItemCommand, model, commandHistory, Messages.MESSAGE_NAME_NOT_FOUND);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteItemByNameCommand deleteFirstCommand = new DeleteItemByNameCommand(new Name("Apple Juice"));
        DeleteItemByNameCommand deleteSecondCommand = new DeleteItemByNameCommand(new Name("Apple Pie"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteItemByNameCommand deleteFirstCommandCopy = new DeleteItemByNameCommand(new Name("Apple Juice"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different ingredient -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
