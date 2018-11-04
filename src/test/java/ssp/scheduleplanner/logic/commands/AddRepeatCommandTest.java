package ssp.scheduleplanner.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.task.Interval;
import ssp.scheduleplanner.model.task.Repeat;

public class AddRepeatCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddRepeatCommand(null, new Repeat("3"), new Interval("3"));
    }

}
