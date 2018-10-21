package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandTestUtil.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.SwappablePanelName;

public class ViewCommandTest {
    private SwappablePanelName panelName = SwappablePanelName.BLANK;
    private ViewCommand command;

    @Before
    public void setup() {
        command = new ViewCommand(panelName);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullColIdx_throwsException() {
        new ViewCommand(null);
    }

    @Test(expected = NullPointerException.class)
    public void execute_nullModel_throwsNullPointerException() throws CommandException {
        command.execute(null, new CommandHistory());
    }

    @Test
    public void execute_normalScenario_successMessage() throws CommandException {
        CommandResult result = command.execute(new ModelStub(), new CommandHistory());
        String formattedPanelName = panelName.toString()
                                             .substring(0, 1)
                                             .toUpperCase()
            + panelName.toString()
                       .toLowerCase()
                       .substring(1);
        assertEquals(result.feedbackToUser,
            String.format(ViewCommand.MESSAGE_VIEW_CHANGED_SUCCESS, formattedPanelName));
    }

    @Test
    public void equals_objectAndItself_returnsTrue() {
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_objectAndEquivalentObject_returnsTrue() {
        assertTrue(command.equals(new ViewCommand(SwappablePanelName.BLANK)));
    }

    @Test
    public void equals_objectAndAnotherObject_returnsFalse() {
        assertFalse(command.equals(new ViewCommand(SwappablePanelName.MEDICATION)));
    }

    @Test
    public void equals_objectAndDifferentType_returnsFalse() {
        assertFalse(command.equals(1));
    }
}
