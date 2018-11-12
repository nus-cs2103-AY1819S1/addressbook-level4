package seedu.address.logic.commands.deliveryman;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeliverymanAtIndex;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.user.TypicalUsers.getTypicalUsersList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeliverymanListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalOrderBook(), getTypicalUsersList(),
            getTypicalDeliverymenList(), new UserPrefs());
        expectedModel = new ModelManager(model.getOrderBook(), model.getUsersList(),
            model.getDeliverymenList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new DeliverymanListCommand(), model, commandHistory,
            DeliverymanListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDeliverymanAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new DeliverymanListCommand(), model, commandHistory,
            DeliverymanListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
