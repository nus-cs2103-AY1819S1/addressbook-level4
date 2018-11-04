package seedu.restaurant.logic.commands.menu;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.menu.MenuCommandTestUtil.showItemAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;

//@@author yican95
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListItemsCommand.
 */
public class ListItemsCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListItemsCommand(), model, commandHistory, ListItemsCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showItemAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListItemsCommand(), model, commandHistory, ListItemsCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
