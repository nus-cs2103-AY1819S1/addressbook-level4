package seedu.address.model.notification;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Snookerballs
public class TipTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tip(null, null));
    }
}
