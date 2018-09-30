package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Context.EVENT_CONTEXT_ID;
import static seedu.address.model.Context.EVENT_CONTEXT_NAME;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SwitchCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_switch_success() {
        SwitchCommand switchCommand = new SwitchCommand(Context.EVENT_CONTEXT_ID);

        String expectedMessage = String.format(SwitchCommand.MESSAGE_SUCCESS, EVENT_CONTEXT_NAME);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCurrentContext(EVENT_CONTEXT_ID);

        assertCommandSuccess(switchCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
