package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;

class ComposeCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ComposeCommand(null);
    }



}
