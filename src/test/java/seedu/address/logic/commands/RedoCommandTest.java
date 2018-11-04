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
public class RedoCommandTest extends DefaultCommandTest{
    private CommandHistory commandHistory = new CommandHistory();
    private RedoCommand redoCommand = new RedoCommand();
    private String messageSuccess = RedoCommand.MESSAGE_SUCCESS;
    private String messageFailure = RedoCommand.MESSAGE_FAILURE;

    @Test
    public void executeDefaultStateSingleRedo_commandFailure() {
        Model model = ModelGenerator.getDefaultModel();
        assertCommandFailure(redoCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSingleRedoPointingAtStart_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandSuccess(redoCommand, model, commandHistory, messageSuccess, 1, 4);
    }

    @Test
    public void executeSingleRedoPointingAtMid_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(redoCommand, model, commandHistory, messageSuccess, 2, 4);
    }

    @Test
    public void executeSingleRedoPointingAtEnd_commandFailure() {
        Model model = ModelGenerator.getModelWithTwoTransformations();
        assertCommandFailure(redoCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSuccessiveRedoPointingAtStart_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandSuccess(redoCommand, model, commandHistory, messageSuccess, 1, 4);
        assertCommandSuccess(redoCommand, model, commandHistory, messageSuccess, 2, 4);
    }

    @Test
    public void executeSingleRedoAfterPurge_commandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        Model purgedModel = ModelGenerator.executeATransformation(model);
        assertCommandFailure(redoCommand, purgedModel, commandHistory, messageFailure);
    }

    @After
    public void cleanUp() {
        clearCache();
    }
}
