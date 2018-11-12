package seedu.superta.logic.commands;

import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        expectedModel.resetData(new SuperTaClient());
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
