package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandTestUtil.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;

public class SortCommandTest {
    private int[] validColIdx;
    private int[] anotherValidColIdx;
    private SortCommand.SortOrder order = SortCommand.SortOrder.ASCENDING;
    private SortCommand command;

    @Before
    public void setup() {
        validColIdx = new int[] {1};
        anotherValidColIdx = new int[] {1, 2};
        command = new SortCommand(order, validColIdx);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullColIdx_throwsNullPointerException() {
        new SortCommand(order, null);
    }

    @Test(expected = NullPointerException.class)
    public void execute_nullModel_throwsNullPointerException() throws CommandException {
        command.execute(null, new CommandHistory());
    }

    @Test
    public void execute_normalScenario_successMessage() throws CommandException {
        CommandResult result = command.execute(new ModelStub(), new CommandHistory());
        assertEquals(result.feedbackToUser, new CommandResult(SortCommand.MESSAGE_VIEW_SORTED_SUCCESS).feedbackToUser);
    }

    @Test
    public void equals_objectAndItself_returnsTrue() {
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_objectAndEquivalentObject_returnsTrue() {
        assertTrue(command.equals(new SortCommand(order, validColIdx)));
    }

    @Test
    public void equals_objectAndAnotherObject_returnsFalse() {
        assertFalse(command.equals(new SortCommand(order, anotherValidColIdx)));
    }

    @Test
    public void equals_objectAndDifferentType_returnsFalse() {
        assertFalse(command.equals(1));
    }
}
