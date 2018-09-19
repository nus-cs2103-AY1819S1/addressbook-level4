package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_1;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_2;

public class RemarkTest {
    Remark sampleRemark1a = new Remark(SAMPLE_REMARK_1);
    Remark sampleRemark1b = new Remark(SAMPLE_REMARK_1);
    Remark sampleRemark2 = new Remark(SAMPLE_REMARK_2);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_emptyRemark_success() {
        assertTrue((new Remark("")).value.equals(""));
    }

    @Test
    public void equals() {
        // same remark object
        assertTrue(sampleRemark1a.equals(sampleRemark1a));

        // different remark objects with same value
        assertTrue(sampleRemark1a.equals(sampleRemark1b));

        // different remark objects with different values
        assertFalse(sampleRemark1a.equals(sampleRemark2));
    }
}
