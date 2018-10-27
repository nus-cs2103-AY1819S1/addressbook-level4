package seedu.clinicio.logic.commands;

import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Test;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;

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
