package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ClinicIo;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.analytics.Analytics;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void execute_emptyClinicIo_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitClinicIo();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel,
            analytics);
    }

    @Test
    public void execute_nonEmptyClinicIo_success() {
        Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalClinicIo(), new UserPrefs());
        expectedModel.resetData(new ClinicIo());
        expectedModel.commitClinicIo();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel,
            analytics);
    }

}
