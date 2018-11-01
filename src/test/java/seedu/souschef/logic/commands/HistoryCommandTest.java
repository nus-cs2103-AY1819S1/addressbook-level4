package seedu.souschef.logic.commands;

import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;

public class HistoryCommandTest {
    private History history = new History();
    private Model model = new ModelSetCoordinator().getRecipeModel();
    private Model expectedModel = new ModelSetCoordinator().getRecipeModel();

    @Test
    public void execute() {
        assertCommandSuccess(new HistoryCommand(), model, history, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel);

        String command1 = "clear";
        history.add(command1);
        assertCommandSuccess(new HistoryCommand(), model, history,
                String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel);

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        assertCommandSuccess(new HistoryCommand(), model, history, expectedMessage, expectedModel);
    }

}
