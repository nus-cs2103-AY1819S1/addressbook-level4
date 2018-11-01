package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class CookTimeTest {

    @Test
    public void constructor_invalidCookTime_throwsIllegalArgumentException() {
        String invalidCookTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CookTime(invalidCookTime));
    }

    @Test
    public void isValidCookTime() {
        // null cook time
        Assert.assertThrows(NullPointerException.class, () -> CookTime.isValidCookTime(null));

        // blank cook time
        assertFalse(CookTime.isValidCookTime("")); // empty string
        assertFalse(CookTime.isValidCookTime(" ")); // spaces only

        // missing parts
        assertFalse(CookTime.isValidCookTime("P20M")); // missing local part
        assertFalse(CookTime.isValidCookTime("30A")); // missing '@' symbol
        assertFalse(CookTime.isValidCookTime("PT")); // missing domain name

        // invalid parts
        assertFalse(CookTime.isValidCookTime("PT20MH")); // invalid domain name

        // valid cook time
        assertTrue(CookTime.isValidCookTime("PT20M"));
        assertTrue(CookTime.isValidCookTime("PT1H20M"));
        assertTrue(CookTime.isValidCookTime("PT30M20S"));
    }
}
