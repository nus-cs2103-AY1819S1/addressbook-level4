package seedu.address.model.leaveapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVEID_BOB_LEAVE;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class LeaveIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new LeaveId(null));
    }

    @Test
    public void constructor_invalidLeaveId_throwsIllegalArgumentException() {
        Integer invalidLeaveId = -1;
        Assert.assertThrows(IllegalArgumentException.class, () -> new LeaveId(invalidLeaveId));
    }

    @Test
    public void isValidLeaveId() {
        // null leave ID
        Assert.assertThrows(NullPointerException.class, () -> LeaveId.isValidLeaveId(null));

        // invalid leave ID
        assertFalse(LeaveId.isValidLeaveId(-1)); // negative number
        assertFalse(LeaveId.isValidLeaveId(-5)); // negative number

        // valid leave ID
        assertTrue(LeaveId.isValidLeaveId(0)); // zero
        assertTrue(LeaveId.isValidLeaveId(1)); // positive number
        assertTrue(LeaveId.isValidLeaveId(20)); // positive number
        assertTrue(LeaveId.isValidLeaveId(100000)); // large positive number
    }

    @Test
    public void toStringTest() {
        assertEquals(new LeaveId(VALID_LEAVEID_BOB_LEAVE).toString(), VALID_LEAVEID_BOB_LEAVE.toString());
        assertEquals(new LeaveId(0).toString(), "0");
        assertEquals(new LeaveId(50).toString(), "50");
        assertEquals(new LeaveId(1000).toString(), "1000");
    }
}
