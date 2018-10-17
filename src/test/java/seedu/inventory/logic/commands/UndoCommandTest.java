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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(),
            new StaffList());
    private final Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(),
            new StaffList());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstItem(model);
        deleteFirstItem(model);

        deleteFirstItem(expectedModel);
        deleteFirstItem(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
