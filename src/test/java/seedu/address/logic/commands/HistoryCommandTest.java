package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBookModelManager;
import seedu.address.model.DiagnosisModelManager;
import seedu.address.model.ScheduleModelManager;

public class HistoryCommandTest {
    @Rule
    public ExpectedException test = ExpectedException.none();

    @Test
    public void history_success() {
        // empty history
        assertEquals(new HistoryCommand().execute(new AddressBookModelManager(),
                new ScheduleModelManager(),
                new DiagnosisModelManager(),
                new CommandHistory()), new CommandResult(HistoryCommand.MESSAGE_NO_HISTORY));

        // non-empty history
        CommandHistory history = new CommandHistory();
        List<String> commands = new LinkedList<>();
        commands.add("cmd1");
        commands.add("cmd2");
        commands.add("cmd3");

        commands.stream().forEach(s -> history.add(s));

        List<String> previousCommands = history.getHistory();
        Collections.reverse(previousCommands);
        assertEquals(new HistoryCommand().execute(new AddressBookModelManager(),
                new ScheduleModelManager(),
                new DiagnosisModelManager(),
                history),
                new CommandResult(String.format(HistoryCommand.MESSAGE_SUCCESS,
                        String.join("\n", previousCommands))));
    }
}
