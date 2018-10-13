package seedu.address.model.task;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StatusTest {

    @Test
    public void StatusContainValidValues() {
        Status[] expectedValues = {Status.IN_PROGRESS, Status.FINISHED, Status.OVERDUE};
        assertArrayEquals(expectedValues, Status.values());
    }

    @Test
    public void isValidStatus() {
        //null status value
        assertFalse(Status.isValidStatus(null));

        //invalid status value
        assertFalse(Status.isValidStatus("work in progress"));
        assertFalse(Status.isValidStatus("finished"));
        assertFalse(Status.isValidStatus("overdueee"));

        //valid status value
        assertTrue(Status.isValidStatus("IN PROGRESS"));
        assertTrue(Status.isValidStatus("FINISHED"));
        assertTrue(Status.isValidStatus("OVERDUE"));
    }

    @Test
    public void getStatusFromValue_returnsStatusObject() {
        //invalid status value
        Assert.assertThrows(IllegalArgumentException.class, () -> Status.getStatusFromValue("in progress"));
        Assert.assertThrows(IllegalArgumentException.class, () -> Status.getStatusFromValue("hello"));

        //valid status value
        assertEquals(Status.getStatusFromValue("IN PROGRESS"), Status.IN_PROGRESS);
        assertEquals(Status.getStatusFromValue("FINISHED"), Status.FINISHED);
        assertEquals(Status.getStatusFromValue("OVERDUE"), Status.OVERDUE);
    }

    @Test
    public void toString_returnsStatusValue() {
        assertEquals(Status.IN_PROGRESS.toString(), "IN PROGRESS");
        assertEquals(Status.FINISHED.toString(), "FINISHED");
        assertEquals(Status.OVERDUE.toString(), "OVERDUE");
    }
}
