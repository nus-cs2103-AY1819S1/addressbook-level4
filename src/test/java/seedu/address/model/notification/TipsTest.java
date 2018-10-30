package seedu.address.model.notification;

import java.util.List;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Snookerballs
public class TipsTest {
    private List<Tip> tips;
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tips(null));
    }

}
