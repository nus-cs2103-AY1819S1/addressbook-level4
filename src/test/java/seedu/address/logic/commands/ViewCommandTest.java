package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.ui.SwappablePanelName;

public class ViewCommandTest {
    private ViewCommand command;

    @Before
    public void setup() {
        command = new ViewCommand(SwappablePanelName.BLANK);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullColIdx_throwsException() {
        new ViewCommand(null);
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
