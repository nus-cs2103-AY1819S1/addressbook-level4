package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalSaveIt;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SaveIt;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptySaveIt_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitSaveIt();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptySaveIt_success() {
        Model model = new ModelManager(getTypicalSaveIt(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSaveIt(), new UserPrefs());
        expectedModel.resetData(new SaveIt());
        expectedModel.commitSaveIt();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
