package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyRestaurantBook_success() {
        // An empty storage assumed to have the root account in it
        Model model = new ModelManager(SampleDataUtil.getSampleRestaurantBook(), new UserPrefs());
        Model expectedModel = new ModelManager(SampleDataUtil.getSampleRestaurantBook(), new UserPrefs());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRestaurantBook_success() {
        Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        // Once reset, only has root account
        expectedModel.resetData(SampleDataUtil.getSampleRestaurantBook());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
