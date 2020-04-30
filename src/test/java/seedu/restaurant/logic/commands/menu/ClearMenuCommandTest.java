package seedu.restaurant.logic.commands.menu;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;

//@@author yican95
public class ClearMenuCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyMenu_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new ClearMenuCommand(), model, commandHistory, ClearMenuCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyMenu_success() {
        Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        expectedModel.resetMenuData(new RestaurantBook());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new ClearMenuCommand(), model, commandHistory, ClearMenuCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

}
