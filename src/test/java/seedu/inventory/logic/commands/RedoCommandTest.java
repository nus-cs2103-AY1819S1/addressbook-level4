package seedu.inventory.logic.commands;

import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.logic.commands.CommandTestUtil.deleteFirstItem;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Before;
import org.junit.Test;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(),
            new StaffList());
    private final Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(),
            new StaffList());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstItem(model);
        deleteFirstItem(model);
        model.undoInventory();
        model.undoInventory();

        deleteFirstItem(expectedModel);
        deleteFirstItem(expectedModel);
        expectedModel.undoInventory();
        expectedModel.undoInventory();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
