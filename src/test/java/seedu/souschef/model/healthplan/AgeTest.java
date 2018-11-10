package seedu.souschef.model.healthplan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class AgeTest {

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        String negativeAge = "-1";
        String zeroAge = "0";
        String blank = "";
        String whiteSpace = " ";
        String validAge = "10";
        String invalidAge = "5";
        String trailingZeroTest = "00";

        //test null case
        Assert.assertThrows(NullPointerException.class, () -> new Age(null));

        //false cases
        assertFalse(Age.isValidAge(negativeAge));
        assertFalse(Age.isValidAge(zeroAge));
        assertFalse(Age.isValidAge(blank));
        assertFalse(Age.isValidAge(whiteSpace));
        assertFalse(Age.isValidAge(invalidAge));
        assertFalse(Age.isValidAge(trailingZeroTest));

        //true case
        assertTrue(Age.isValidAge(validAge));

    }


}
