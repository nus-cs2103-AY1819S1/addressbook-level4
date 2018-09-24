package seedu.address.model.record;

import org.junit.Test;

import seedu.address.testutil.Assert;

/**
 * Note that VolunteerId will be from Volunteers. All tests should be done there.
 */
public class VolunteerIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new VolunteerId(null));
    }

    @Test
    public void constructor_invalidVolunteerId_throwsIllegalArgumentException() {
        String invalidVolunteerId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new VolunteerId(invalidVolunteerId));
    }
}
