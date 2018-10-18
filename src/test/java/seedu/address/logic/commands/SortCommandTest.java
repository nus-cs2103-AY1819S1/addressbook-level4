package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SortCommandTest {
    private int[] validColIdx;
    private int[] anotherValidColIdx;
    private SortCommand command;

    @Before
    public void setup() {
        validColIdx = new int[] {1};
        anotherValidColIdx = new int[] {1, 2};
        command = new SortCommand(validColIdx);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullColIdx_throwsException() {
        new SortCommand(null);
    }

    @Test
    public void equals_objectAndItself_returnsTrue() {
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_objectAndEquivalentObject_returnsTrue() {
        assertTrue(command.equals(new SortCommand(validColIdx)));
    }

    @Test
    public void equals_objectAndAnotherObject_returnsFalse() {
        assertFalse(command.equals(new SortCommand(anotherValidColIdx)));
    }

    @Test
    public void equals_objectAndDifferentType_returnsFalse() {
        assertFalse(command.equals(1));
    }
}
