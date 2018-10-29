package seedu.address.model.notification;

import seedu.address.testutil.Assert;

import org.junit.Test;

import java.util.List;

public class TipsTest {
    private List<Tip> tips;
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tips(null));
    }

}
