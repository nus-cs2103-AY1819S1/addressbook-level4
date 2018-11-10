package seedu.souschef.model.healthplan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;


public class DurationTest {
    @Test
    public void constructor_invalidDuration_throwsIllegalArgumentException() {
        String invalidDuration = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Age(invalidDuration));
    }


    @Test
    public void isValidDuration() {
        String negativeDuration = "-1";
        String zeroDuration = "0";
        String blank = "";
        String whiteSpace = " ";
        String validDuration = "5";
        String trailingZeroTest = "00";

        //test null case
        Assert.assertThrows(NullPointerException.class, () -> new Duration(null));

        //false cases
        assertFalse(Duration.isValidDuration(negativeDuration));
        assertFalse(Duration.isValidDuration(zeroDuration));
        assertFalse(Duration.isValidDuration(blank));
        assertFalse(Duration.isValidDuration(whiteSpace));
        assertFalse(Duration.isValidDuration(trailingZeroTest));

        //true case
        assertTrue(Duration.isValidDuration(validDuration));
    }





}
