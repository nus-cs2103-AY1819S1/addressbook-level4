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
public class RedoAllCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private RedoAllCommand redoAllCommand = new RedoAllCommand();
    private String messageSuccess = RedoAllCommand.MESSAGE_SUCCESS;
    private String messageFailure = RedoAllCommand.MESSAGE_FAILURE;

    @Test
    public void executeDefaultStateRedoAllCommandFailure() {
        Model model = ModelGenerator.getDefaultModel();
        assertCommandFailure(redoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeRedoAllPointingAtStartCommandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandSuccess(redoAllCommand, model, commandHistory, messageSuccess, 3, 4);
    }

    @Test
    public void executeRedoAllPointingAtMidCommandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(redoAllCommand, model, commandHistory, messageSuccess, 3, 4);
    }

    @Test
    public void executeRedoAllPointingAtEndCommandFailure() {
        Model model = ModelGenerator.getModelWithTwoTransformations();
        assertCommandFailure(redoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSuccessiveRedoAllPointingAtStartCommandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandSuccess(redoAllCommand, model, commandHistory, messageSuccess, 3, 4);
        assertCommandFailure(redoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSuccessiveRedoAllPointingAtMidCommandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(redoAllCommand, model, commandHistory, messageSuccess, 3, 4);
        assertCommandFailure(redoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeRedoAllAfterPurgeCommandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        Model purgedModel = ModelGenerator.executeATransformation(model);
        assertCommandFailure(redoAllCommand, purgedModel, commandHistory, messageFailure);
    }

    @After
    public void cleanUp() {
        clearCache();
    }
}
