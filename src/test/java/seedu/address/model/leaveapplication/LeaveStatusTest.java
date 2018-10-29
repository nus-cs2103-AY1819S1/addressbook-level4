package seedu.address.model.leaveapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class LeaveStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new LeaveStatus(null));
    }

    @Test
    public void constructor_invalidLeaveStatus_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> StatusEnum.Status.valueOf("SELF-APPROVED"));
    }

    @Test
    public void isValidLeaveStatus() {
        // null leave status
        Assert.assertThrows(NullPointerException.class, () -> LeaveStatus.isValidStatus(null));

        // invalid leave status
        assertFalse(LeaveStatus.isValidStatus("")); // empty string
        assertFalse(LeaveStatus.isValidStatus(" ")); // whitespace
        assertFalse(LeaveStatus.isValidStatus("RANDOMTEXT")); // some random text

        // valid leave status
        assertTrue(LeaveStatus.isValidStatus("PENDING"));
        assertTrue(LeaveStatus.isValidStatus("APPROVED"));
        assertTrue(LeaveStatus.isValidStatus("REJECTED"));
        assertTrue(LeaveStatus.isValidStatus("CANCELLED"));
    }

    @Test
    public void toStringTest() {
        assertEquals(new LeaveStatus("APPROVED").toString(), "APPROVED");
        assertEquals(new LeaveStatus("CANCELLED").toString(), "CANCELLED");
        assertEquals(new LeaveStatus("PENDING").toString(), "PENDING");
        assertEquals(new LeaveStatus("REJECTED").toString(), "REJECTED");
    }
}
