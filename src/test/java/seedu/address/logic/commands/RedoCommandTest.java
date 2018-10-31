package seedu.address.logic.commands;

import static seedu.address.testutil.UndoRedoCommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.UndoRedoCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.UndoRedoCommandTestUtil.clearCache;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

public class RedoCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private RedoCommand redoCommand = new RedoCommand();
/*
    @Test
    public void execute_defaultStateHasNothingToRedo() {
        Model model = ModelGenerator.getDefaultModel();
        assertCommandFailure(redoCommand, model, commandHistory, "No more commands to redo!");
    }

    @Test
    public void execute_lastStateHasNothingToRedo() {
        Model model = ModelGenerator.getModelWithTwoTransformations();
        assertCommandFailure(redoCommand, model, commandHistory, "No more commands to redo!");
    }

    @Test
    public void execute_singleRedo() {
        Model model = ModelGenerator.getModelWithUndoneStates();
        assertCommandSuccess(redoCommand, model, commandHistory, "Redo success!", 1, 4);
    }

    @Test
    public void execute_successiveRedo() {
        Model model = ModelGenerator.getModelWithUndoneStates();
        assertCommandSuccess(redoCommand, model, commandHistory, "Redo success!", 1, 4);
        assertCommandSuccess(redoCommand, model, commandHistory, "Redo success!", 2, 4);
    }
*/
    @After
    public void cleanUp() {
        clearCache();
    }
}
