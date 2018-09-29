package seedu.address.model.person;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatusTest {
    
    @Test
    public void status_contains_correct_range_of_values() {
        Status[] expectedValues = {Status.IN_PROGRESS, Status.FINISHED};
        assertArrayEquals(expectedValues, Status.values());
    }

    @Test
    public void toStringTest() {
        assertEquals(Status.IN_PROGRESS.toString(), "IN PROGRESS");
        assertEquals(Status.FINISHED.toString(), "FINISHED");
    }
}