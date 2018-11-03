
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
public class UndoAllCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private UndoAllCommand undoAllCommand = new UndoAllCommand();
    private String messageSuccess = UndoAllCommand.MESSAGE_SUCCESS;
    private String messageFailure = UndoAllCommand.MESSAGE_FAILURE;

    @Test
    public void executeDefaultStateUndoAll_commandFailure() {
        Model model = ModelGenerator.getDefaultModel();
        assertCommandFailure(undoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeUndoAllPointingAtStart_commandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        assertCommandFailure(undoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeUndoAllPointingAtMid_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(undoAllCommand, model, commandHistory, messageSuccess, 0, 4);
    }

    @Test
    public void executeUndoAllWithPointerAtEnd_commandSuccess() {
        Model model = ModelGenerator.getModelWithThreeTransformations();
        assertCommandSuccess(undoAllCommand, model, commandHistory, messageSuccess, 0, 4);
    }

    @Test
    public void execute_successiveUndoAllPointingAtMid_commandFailure() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtMid();
        assertCommandSuccess(undoAllCommand, model, commandHistory, messageSuccess, 0, 4);
        assertCommandFailure(undoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void execute_successiveUndoAllPointingAtEnd_commandFailure() {
        Model model = ModelGenerator.getModelWithThreeTransformations();
        assertCommandSuccess(undoAllCommand, model, commandHistory, messageSuccess, 0, 4);
        assertCommandFailure(undoAllCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void executeUndoAllAfterPurge_commandSuccess() {
        Model model = ModelGenerator.getModelWithUndoneStatesPointingAtStart();
        Model purgedModel = ModelGenerator.executeATransformation(model);
        assertCommandSuccess(undoAllCommand, purgedModel, commandHistory, messageSuccess, 0, 2);
    }

    @After
    public void cleanUp() {
        clearCache();
    }
}

