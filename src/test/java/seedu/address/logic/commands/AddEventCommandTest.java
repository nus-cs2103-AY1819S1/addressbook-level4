package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AddEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_failure() {
        AddEventCommand addEventCommand = new AddEventCommand();

        assertCommandFailure(addEventCommand, model, commandHistory,
                addEventCommand.MESSAGE_METHOD_NOT_IMPLEMENTED_YET);
    }
}
