package seedu.address.logic.commands;
import static seedu.address.logic.commands.SaveCommand.MESSAGE_USAGE;
import static seedu.address.testutil.TypicalWishes.getTypicalWishBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SaveCommandTest {
    Model model = new ModelManager(getTypicalWishBook(), new UserPrefs());

    @Test
    public void execute() {
        CommandTestUtil.assertCommandFailure(new SaveCommand(), model, new CommandHistory(), MESSAGE_USAGE);
    }
}
