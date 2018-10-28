package ssp.scheduleplanner.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.task.Repeat;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.testutil.TaskBuilder;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AddRepeatCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddRepeatCommand(null, new Repeat("3"));
    }

}
