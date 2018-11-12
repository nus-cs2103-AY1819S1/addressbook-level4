package seedu.address.model.leaveapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB_LEAVE;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // space only
        assertFalse(Description.isValidDescription("    ")); // spaces only

        // valid description
        assertTrue(Description.isValidDescription("Cousin's wedding")); // alphabets only
        assertTrue(Description.isValidDescription("    Long weekend break")); // spaces in front
        assertTrue(Description.isValidDescription("Daughter's 21st birthday")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Family holiday to Maldives. Looking forward to "
                + "some much needed family time!")); // long descriptions
    }

    @Test
    public void toStringTest() {
        assertEquals(new Description(VALID_DESCRIPTION_BOB_LEAVE).toString(), VALID_DESCRIPTION_BOB_LEAVE);
        assertEquals(new Description("Test description").toString(), "Test description");
    }
}
