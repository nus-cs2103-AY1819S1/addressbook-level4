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
public class RedoAllCommandTest extends DefaultCommandTest{
    private CommandHistory commandHistory = new CommandHistory();
    private RedoAllCommand redoAllCommand = new RedoAllCommand();
    private String messageSuccess = RedoAllCommand.MESSAGE_SUCCESS;
    private String messageFailure = RedoAllCommand.MESSAGE_FAILURE;

    @Test
    public void executeDefaultStateRedoAll_commandFailure() {
        Model model = ModelGenerator.getDefaultModel();
        assertCommandFailure(redoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeRedoAllPointingAtStart_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandSuccess(redoAllCommand, model, commandHistory, messageSuccess, 3, 4);
    }

    @Test
    public void executeRedoAllPointingAtMid_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(redoAllCommand, model, commandHistory, messageSuccess, 3, 4);
    }

    @Test
    public void executeRedoAllPointingAtEnd_commandFailure() {
        Model model = ModelGenerator.getModelWithTwoTransformations();
        assertCommandFailure(redoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSuccessiveRedoAllPointingAtStart_commandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandSuccess(redoAllCommand, model, commandHistory, messageSuccess, 3, 4);
        assertCommandFailure(redoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSuccessiveRedoAllPointingAtMid_commandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(redoAllCommand, model, commandHistory, messageSuccess, 3, 4);
        assertCommandFailure(redoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeRedoAllAfterPurge_commandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        Model purgedModel = ModelGenerator.executeATransformation(model);
        assertCommandFailure(redoAllCommand, purgedModel, commandHistory, messageFailure);
    }

    @After
    public void cleanUp() {
        clearCache();
    }
}
