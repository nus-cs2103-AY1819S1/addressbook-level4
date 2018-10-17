package seedu.inventory.logic.commands;

import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.inventory.testutil.TypicalItems.getTypicalInventory;

import org.junit.Before;
import org.junit.Test;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.item.Item;
import seedu.inventory.testutil.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalInventory(), new UserPrefs(), new SaleList(), new StaffList());
    }

    @Test
    public void execute_newItem_success() {
        Item validItem = new ItemBuilder().build();

        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs(), new SaleList(), new StaffList());
        expectedModel.addItem(validItem);
        expectedModel.commitInventory();

        assertCommandSuccess(new AddCommand(validItem), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validItem), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Item itemInList = model.getInventory().getItemList().get(0);
        assertCommandFailure(new AddCommand(itemInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_ITEM);
    }

}
