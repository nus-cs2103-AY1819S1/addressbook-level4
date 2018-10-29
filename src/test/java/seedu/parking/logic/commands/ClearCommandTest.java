package seedu.parking.logic.commands;

import static seedu.parking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;

import org.junit.Test;

import seedu.parking.logic.CommandHistory;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyCarparkFinder_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitCarparkFinder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_EMPTY, expectedModel);
    }

    @Test
    public void execute_nonEmptyCarparkFinder_success() {
        Model model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
        expectedModel.resetData(new CarparkFinder());
        expectedModel.commitCarparkFinder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
