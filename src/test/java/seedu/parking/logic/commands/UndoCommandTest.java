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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstCarpark(model);
        deleteFirstCarpark(model);

        deleteFirstCarpark(expectedModel);
        deleteFirstCarpark(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoCarparkFinder();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoCarparkFinder();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
