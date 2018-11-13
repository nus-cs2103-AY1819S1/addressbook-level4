package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HistoryCommandTest {
    private CommandHistory history = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_showCommands_success() {
        assertCommandSuccess(new HistoryCommand(HistoryCommand.HistoryType.SHOW_COMMANDS), model,
                history, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel);

        String command1 = "clear";
        history.add(command1);
        assertCommandSuccess(new HistoryCommand(HistoryCommand.HistoryType.SHOW_COMMANDS), model, history,
                String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel);

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        assertCommandSuccess(new HistoryCommand(HistoryCommand.HistoryType.SHOW_COMMANDS), model,
                history, expectedMessage, expectedModel);
    }

    @Test
    public void execute_showSavingsCommands_success() {
        assertCommandSuccess(new HistoryCommand(HistoryCommand.HistoryType.SHOW_SAVINGS), model,
                history, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel);

        String command1 = "clear";
        history.add(command1);
        assertCommandSuccess(new HistoryCommand(HistoryCommand.HistoryType.SHOW_SAVINGS), model, history,
                HistoryCommand.MESSAGE_NO_SAVINGS_HISTORY, expectedModel);

        String command2 = "save 1 1";
        String command3 = "select 1";
        String command4 = "save 1 2";
        history.add(command2);
        history.add(command3);
        history.add(command4);
        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS_SAVE_COMMANDS,
                String.join("\n", command4, command2));
        assertCommandSuccess(new HistoryCommand(HistoryCommand.HistoryType.SHOW_SAVINGS), model,
                history, expectedMessage, expectedModel);
    }
}
