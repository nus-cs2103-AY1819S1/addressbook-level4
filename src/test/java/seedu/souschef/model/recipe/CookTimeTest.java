package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class CookTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CookTime(null));
    }

    @Test
    public void constructor_invalidCookTime_throwsIllegalArgumentException() {
        String invalidCookTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CookTime(invalidCookTime));
    }

    @Test
    public void isValidCookTime() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> CookTime.isValidCookTime(null));

        // blank email
        assertFalse(CookTime.isValidCookTime("")); // empty string
        assertFalse(CookTime.isValidCookTime(" ")); // spaces only

        // missing parts
        assertFalse(CookTime.isValidCookTime("@example.com")); // missing local part
        assertFalse(CookTime.isValidCookTime("peterjackexample.com")); // missing '@' symbol
        assertFalse(CookTime.isValidCookTime("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(CookTime.isValidCookTime("peterjack@-")); // invalid domain name
        assertFalse(CookTime.isValidCookTime("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(CookTime.isValidCookTime("peter jack@example.com")); // spaces in local part
        assertFalse(CookTime.isValidCookTime("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(CookTime.isValidCookTime(" peterjack@example.com")); // leading space
        assertFalse(CookTime.isValidCookTime("peterjack@example.com ")); // trailing space
        assertFalse(CookTime.isValidCookTime("peterjack@@example.com")); // double '@' symbol
        assertFalse(CookTime.isValidCookTime("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(CookTime.isValidCookTime("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(CookTime.isValidCookTime("peterjack@.example.com")); // domain name starts with a period
        assertFalse(CookTime.isValidCookTime("peterjack@example.com.")); // domain name ends with a period
        assertFalse(CookTime.isValidCookTime("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(CookTime.isValidCookTime("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(CookTime.isValidCookTime("PeterJack_1190@example.com"));
        assertTrue(CookTime.isValidCookTime("a@bc")); // minimal
        assertTrue(CookTime.isValidCookTime("test@localhost")); // alphabets only
        assertTrue(CookTime.isValidCookTime("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(CookTime.isValidCookTime("123@145")); // numeric local part and domain name
        assertTrue(CookTime.isValidCookTime("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(CookTime.isValidCookTime("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(CookTime.isValidCookTime("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
