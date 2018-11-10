package seedu.address.logic.commands;

import static seedu.address.testutil.UndoRedoCommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.UndoRedoCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.UndoRedoCommandTestUtil.clearCache;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

//@@author ihwk1996
public class RedoCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private RedoCommand redoCommand = new RedoCommand();
    private String messageSuccess = RedoCommand.MESSAGE_SUCCESS;
    private String messageFailure = RedoCommand.MESSAGE_FAILURE;

    @Test
    public void executeDefaultStateSingleRedoCommandFailure() {
        Model model = ModelGenerator.getDefaultModel();
        assertCommandFailure(redoCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSingleRedoPointingAtStartCommandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandSuccess(redoCommand, model, commandHistory, messageSuccess, 1, 4);
    }

    @Test
    public void executeSingleRedoPointingAtMidCommandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(redoCommand, model, commandHistory, messageSuccess, 2, 4);
    }

    @Test
    public void executeSingleRedoPointingAtEndCommandFailure() {
        Model model = ModelGenerator.getModelWithTwoTransformations();
        assertCommandFailure(redoCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSuccessiveRedoPointingAtStartCommandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandSuccess(redoCommand, model, commandHistory, messageSuccess, 1, 4);
        assertCommandSuccess(redoCommand, model, commandHistory, messageSuccess, 2, 4);
    }

    @Test
    public void executeSingleRedoAfterPurgeCommandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        Model purgedModel = ModelGenerator.executeATransformation(model);
        assertCommandFailure(redoCommand, purgedModel, commandHistory, messageFailure);
    }

    @After
    public void cleanUp() {
        clearCache();
    }
}
