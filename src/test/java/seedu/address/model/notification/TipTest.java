package seedu.address.model.notification;

import seedu.address.testutil.Assert;

import org.junit.Test;

public class TipTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tip(null, null));
    }
}
