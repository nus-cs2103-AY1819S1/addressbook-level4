package seedu.inventory.logic.commands;

import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Test;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyInventory_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitInventory();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyInventory_success() {
        Model model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(), new StaffList());
        Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(), new StaffList());
        expectedModel.resetData(new Inventory());
        expectedModel.commitInventory();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
