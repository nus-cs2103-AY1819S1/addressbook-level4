package seedu.parking.logic.commands;

import static seedu.parking.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.parking.logic.commands.CommandTestUtil.deleteFirstCarpark;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;

import org.junit.Before;
import org.junit.Test;

import seedu.parking.logic.CommandHistory;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstCarpark(model);
        deleteFirstCarpark(model);
        model.undoCarparkFinder();
        model.undoCarparkFinder();

        deleteFirstCarpark(expectedModel);
        deleteFirstCarpark(expectedModel);
        expectedModel.undoCarparkFinder();
        expectedModel.undoCarparkFinder();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoCarparkFinder();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoCarparkFinder();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
