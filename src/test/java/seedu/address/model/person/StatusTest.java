package seedu.address.model.person;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StatusTest {
    
    @Test
    public void status_contains_correct_range_of_values() {
        Status[] expectedValues = {Status.IN_PROGRESS, Status.FINISHED};
        assertArrayEquals(expectedValues, Status.values());
    }

    @Test
    public void isValidStatus() {
        //null status value
        assertFalse(Status.isValidStatus(null));

        //invalid status value
        assertFalse(Status.isValidStatus("work in progress"));
        assertFalse(Status.isValidStatus("finished"));
        
        //valid status value
        assertTrue(Status.isValidStatus("IN PROGRESS"));
        assertTrue(Status.isValidStatus("FINISHED"));
    }

    @Test
    public void valid_status_values_give_status_object() {
        //invalid status value
        Assert.assertThrows(IllegalArgumentException.class, () -> Status.getStatusFromValue("in progress"));
        Assert.assertThrows(IllegalArgumentException.class, () -> Status.getStatusFromValue("hello"));

        //valid status value
        assertEquals(Status.getStatusFromValue("IN PROGRESS"), Status.IN_PROGRESS);
        assertEquals(Status.getStatusFromValue("FINISHED"), Status.FINISHED);
    }

    @Test
    public void toStringTest() {
        assertEquals(Status.IN_PROGRESS.toString(), "IN PROGRESS");
        assertEquals(Status.FINISHED.toString(), "FINISHED");
    }
}