package seedu.address.model.wish;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_1;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_1B;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_2;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark((String) null));
        Assert.assertThrows(NullPointerException.class, () -> new Remark((Remark) null));
    }

    @Test
    public void constructor_emptyRemark_success() {
        assertTrue((new Remark("")).value.equals(""));
    }

    @Test
    public void equals() {
        // same remark object
        assertTrue(SAMPLE_REMARK_1.equals(SAMPLE_REMARK_1));

        // different remark objects with same value
        assertTrue(SAMPLE_REMARK_1.equals(SAMPLE_REMARK_1B));

        // different remark objects with different values
        assertFalse(SAMPLE_REMARK_1.equals(SAMPLE_REMARK_2));
    }

    @Test
    public void hashCodeTest() {
        assertTrue(SAMPLE_REMARK_1.hashCode() == SAMPLE_REMARK_1.hashCode());
    }
}
