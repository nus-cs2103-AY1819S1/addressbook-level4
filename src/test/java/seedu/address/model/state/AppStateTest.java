package seedu.address.model.state;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ExitCommand;

public class AppStateTest {

    @Test
    public void isCommandPermitted() {
        AppState.setAppState(State.NORMAL);

        assertTrue(AppState.isCommandPermitted(AddCommand.COMMAND_WORD));
        assertTrue(AppState.isCommandPermitted(ExitCommand.COMMAND_WORD));

        assertFalse(AppState.isCommandPermitted("NotACommand"));
    }
}
