package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CompleteCommandTest {

    private Model model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    @Ignore
    public void execute_completeSingleTask() {

    }

    @Test
    @Ignore
    public void execute_completeDuplicateTask_failure() {

    }


    @Test
    @Ignore
    public void executeUndoRedo_uncompletedATask() {

    }

    @Test
    @Ignore
    public void executeUndoRedo_completedATaskViaRedo() {

    }

}
