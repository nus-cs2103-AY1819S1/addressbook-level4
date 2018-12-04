package seedu.thanepark.logic.commands;

import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.logic.commands.CommandTestUtil.deleteFirstRide;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Before;
import org.junit.Test;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstRide(model);
        deleteFirstRide(model);
        model.undoThanePark();
        model.undoThanePark();

        deleteFirstRide(expectedModel);
        deleteFirstRide(expectedModel);
        expectedModel.undoThanePark();
        expectedModel.undoThanePark();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoThanePark();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoThanePark();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
