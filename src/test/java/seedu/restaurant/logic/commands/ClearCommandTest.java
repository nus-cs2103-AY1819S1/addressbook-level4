package seedu.restaurant.logic.commands;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.util.SampleDataUtil;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyRestaurantBook_success() {
        // An empty storage assumed to have the root account in it
        Model model = new ModelManager(SampleDataUtil.getDefaultRestaurantBook(), new UserPrefs());
        Model expectedModel = new ModelManager(SampleDataUtil.getDefaultRestaurantBook(), new UserPrefs());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRestaurantBook_success() {
        Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        // Once reset, only has root account
        expectedModel.resetData(SampleDataUtil.getDefaultRestaurantBook());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
