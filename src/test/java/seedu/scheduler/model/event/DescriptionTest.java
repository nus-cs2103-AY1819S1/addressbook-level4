package seedu.scheduler.model.event;

import org.junit.Test;

import seedu.scheduler.testutil.Assert;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }
}
