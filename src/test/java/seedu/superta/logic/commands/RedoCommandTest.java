package seedu.superta.logic.commands;

import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Before;
import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);
        model.undoSuperTaClient();
        model.undoSuperTaClient();

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        expectedModel.undoSuperTaClient();
        expectedModel.undoSuperTaClient();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoSuperTaClient();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoSuperTaClient();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
