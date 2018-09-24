package seedu.address.model.record;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Note that EventId will be from Event. All tests should be done there.
 */
public class EventIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventId(null));
    }

    @Test
    public void constructor_invalidEventId_throwsIllegalArgumentException() {
        String invalidEventId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventId(invalidEventId));
    }
}
