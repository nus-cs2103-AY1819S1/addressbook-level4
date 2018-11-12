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
        assertFalse(CookTime.isValidCookTime("  ")); // spaces only

        // missing parts for single unit
        assertFalse(CookTime.isValidCookTime("20")); // missing unit
        assertFalse(CookTime.isValidCookTime("100")); // missing unit
        assertFalse(CookTime.isValidCookTime("0")); // missing unit
        assertFalse(CookTime.isValidCookTime("S")); // missing number
        assertFalse(CookTime.isValidCookTime("M")); // missing number
        assertFalse(CookTime.isValidCookTime("H")); // missing number

        // invalid parts
        assertFalse(CookTime.isValidCookTime("20E")); // invalid unit
        assertFalse(CookTime.isValidCookTime("356TH20M")); // invalid unit
        assertFalse(CookTime.isValidCookTime("34#2S")); // invalid symbol
        assertFalse(CookTime.isValidCookTime("34# 2H")); // invalid space

        // invalid unit combination
        assertFalse(CookTime.isValidCookTime("20H30S")); // invalid unit
        assertFalse(CookTime.isValidCookTime("2349H204S")); // invalid unit
        assertFalse(CookTime.isValidCookTime("23H49M204S")); // invalid unit

        // invalid order
        assertFalse(CookTime.isValidCookTime("20S40M")); // invalid order
        assertFalse(CookTime.isValidCookTime("30M2H")); // invalid order

        // valid cook time
        assertTrue(CookTime.isValidCookTime("20M"));
        assertTrue(CookTime.isValidCookTime("1H20M"));
        assertTrue(CookTime.isValidCookTime("30M20S"));
        assertTrue(CookTime.isValidCookTime("329840M203874S"));
        assertTrue(CookTime.isValidCookTime("329840H203874M"));
        assertTrue(CookTime.isValidCookTime(CookTime.ZERO_COOKTIME));
    }
}
