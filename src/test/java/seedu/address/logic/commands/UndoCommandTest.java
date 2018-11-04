
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
public class UndoCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private UndoCommand undoCommand = new UndoCommand();
    private String messageSuccess = UndoCommand.MESSAGE_SUCCESS;
    private String messageFailure = UndoCommand.MESSAGE_FAILURE;

    @Test
    public void executeDefaultStateSingleUndo_commandFailure() {
        Model model = ModelGenerator.getDefaultModel();
        assertCommandFailure(undoCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSingleUndoPointingAtStart_commandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandFailure(undoCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeSingleUndoPointingAtMid_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 0, 4);
    }

    @Test
    public void executeSingleUndoPointingAtEnd_commandSuccess() {
        Model model = ModelGenerator.getModelWithOneTransformation();
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 0, 2);
    }

    @Test
    public void executeSuccessiveUndoPointingAtEnd_commandSuccess() {
        Model model = ModelGenerator.getModelWithTwoTransformations();
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 1, 3);
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 0, 3);
    }

    @Test
    public void executeSingleUndoAfterPurge_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        Model purgedModel = ModelGenerator.executeATransformation(model);
        assertCommandSuccess(undoCommand, purgedModel, commandHistory, messageSuccess, 0, 2);
    }

    @After
    public void cleanUp() {
        clearCache();
    }
}

