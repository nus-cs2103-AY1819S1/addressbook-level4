package seedu.souschef.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class HistoryTest {
    private History history;

    @Before
    public void setUp() {
        history = new History();
    }

    @Test
    public void constructor_withCommandHistory_copiesCommandHistory() {
        final History historyWithA = new History();
        historyWithA.add("a");

        assertEquals(historyWithA, new History(historyWithA));
    }

    @Test
    public void add() {
        final String validCommand = "clear";
        final String invalidCommand = "adds Bob";

        history.add(validCommand);
        history.add(invalidCommand);
        assertEquals(Arrays.asList(validCommand, invalidCommand), history.getHistory());
    }

    @Test
    public void equals() {
        final History historyWithA = new History();
        historyWithA.add("a");
        final History anotherHistoryWithA = new History();
        anotherHistoryWithA.add("a");
        final History historyWithB = new History();
        historyWithB.add("b");

        // same object -> returns true
        assertTrue(historyWithA.equals(historyWithA));

        // same values -> returns true
        assertTrue(historyWithA.equals(anotherHistoryWithA));

        // null -> returns false
        assertFalse(historyWithA.equals(null));

        // different types -> returns false
        assertFalse(historyWithA.equals(5.0f));

        // different values -> returns false
        assertFalse(historyWithA.equals(historyWithB));
    }

    @Test
    public void hashcode() {
        final History historyWithA = new History();
        historyWithA.add("a");
        final History anotherHistoryWithA = new History();
        anotherHistoryWithA.add("a");
        final History historyWithB = new History();
        historyWithB.add("b");

        // same values -> returns same hashcode
        assertEquals(historyWithA.hashCode(), anotherHistoryWithA.hashCode());

        // different values -> returns different hashcode
        assertNotEquals(historyWithA.hashCode(), historyWithB.hashCode());
    }
}
