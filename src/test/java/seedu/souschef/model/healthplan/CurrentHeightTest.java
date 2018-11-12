package seedu.souschef.model.healthplan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class CurrentHeightTest {

    @Test
    public void constructor_invalidCurrentHeight_throwsIllegalArgumentException() {
        String invalidHeight = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CurrentHeight(invalidHeight));
    }


    @Test
    public void isValidCurrentHeight() {
        String negativeCurrentHeight = "-1";
        String zeroCurrentHeight = "0";
        String blank = "";
        String whiteSpace = " ";
        String validCurrentHeight = "100";
        String invalidSingleDigitAge = "5";
        String invalidDoubleDigitAge = "15";
        String trailingZeroTest = "000";

        //test null case
        Assert.assertThrows(NullPointerException.class, () -> new CurrentHeight(null));

        //false cases
        assertFalse(CurrentHeight.isValidHeight(negativeCurrentHeight));
        assertFalse(CurrentHeight.isValidHeight(zeroCurrentHeight));
        assertFalse(CurrentHeight.isValidHeight(blank));
        assertFalse(CurrentHeight.isValidHeight(whiteSpace));
        assertFalse(CurrentHeight.isValidHeight(invalidSingleDigitAge));
        assertFalse(CurrentHeight.isValidHeight(invalidDoubleDigitAge));
        assertFalse(CurrentHeight.isValidHeight(trailingZeroTest));

        //true case
        assertTrue(CurrentHeight.isValidHeight(validCurrentHeight));

    }



}
