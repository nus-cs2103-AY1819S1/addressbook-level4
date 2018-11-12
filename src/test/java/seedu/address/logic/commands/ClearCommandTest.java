package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Ssenisub;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptySsenisub_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitSsenisub();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptySsenisub_success() {
        Model model = new ModelManager(getTypicalSsenisub(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSsenisub(), new UserPrefs());
        expectedModel.resetData(new Ssenisub());
        expectedModel.commitSsenisub();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
