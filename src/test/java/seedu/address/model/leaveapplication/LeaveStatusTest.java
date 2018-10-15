package seedu.address.model.leaveapplication;

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
        Assert.assertThrows(IllegalArgumentException.class, () -> StatusEnum.Status.valueOf(""));
        Assert.assertThrows(IllegalArgumentException.class, () -> StatusEnum.Status.valueOf(" "));
        Assert.assertThrows(IllegalArgumentException.class, () -> StatusEnum.Status.valueOf("HELLO"));

        // valid leave status
        assertTrue(LeaveStatus.isValidStatus(StatusEnum.Status.valueOf("PENDING"))); // valid leave status values
        assertTrue(LeaveStatus.isValidStatus(StatusEnum.Status.valueOf("APPROVED"))); // valid leave status values
        assertTrue(LeaveStatus.isValidStatus(StatusEnum.Status.valueOf("REJECTED"))); // valid leave status values
        assertTrue(LeaveStatus.isValidStatus(StatusEnum.Status.valueOf("CANCELLED"))); // valid leave status values
    }
}
