package seedu.souschef.model.healthplan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class TargetWeightTest {

    @Test
    public void constructor_invalidTargetWeight_throwsIllegalArgumentException() {
        String invalidWeight = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TargetWeight(invalidWeight));
    }

    @Test
    public void isValidTargetWeight() {
        String negativeTargetDoubleWeight = "-60.0";
        String negativeTargetWeight = "-60";
        String zeroTargetWeight = "0";
        String blank = "";
        String whiteSpace = " ";
        String trailingZeroTest = "00.0";

        String validDoubleTargetWeight = "60.05";
        String validTargetWeight = "60";

        //test null case
        Assert.assertThrows(NullPointerException.class, () -> new TargetWeight(null));

        //false cases
        assertFalse(TargetWeight.isValidWeight(negativeTargetDoubleWeight));
        assertFalse(TargetWeight.isValidWeight(negativeTargetWeight));
        assertFalse(TargetWeight.isValidWeight(zeroTargetWeight));
        assertFalse(TargetWeight.isValidWeight(blank));
        assertFalse(TargetWeight.isValidWeight(whiteSpace));
        assertFalse(TargetWeight.isValidWeight(trailingZeroTest));

        //true case
        assertTrue(TargetWeight.isValidWeight(validDoubleTargetWeight));
        assertTrue(TargetWeight.isValidWeight(validTargetWeight));
    }


}
