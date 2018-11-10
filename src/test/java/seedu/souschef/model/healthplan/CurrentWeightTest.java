package seedu.souschef.model.healthplan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class CurrentWeightTest {

    @Test
    public void constructor_invalidCurrentWeight_throwsIllegalArgumentException() {
        String invalidWeight = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CurrentWeight(invalidWeight));
    }

    @Test
    public void isValidCurrentWeight() {
        String negativeCurrentWeight = "-60.0";
        String zeroCurrentWeight = "0";
        String blank = "";
        String whiteSpace = " ";
        String trailingZeroTest = "00";

        String validDoubleCurrentWeight = "60.05";
        String validCurrentWeight = "60";

        //test null case
        Assert.assertThrows(NullPointerException.class, () -> new CurrentWeight(null));

        //false cases
        assertFalse(CurrentWeight.isValidWeight(negativeCurrentWeight));
        assertFalse(CurrentWeight.isValidWeight(zeroCurrentWeight));
        assertFalse(CurrentWeight.isValidWeight(blank));
        assertFalse(CurrentWeight.isValidWeight(whiteSpace));
        assertFalse(CurrentWeight.isValidWeight(trailingZeroTest));

        //true case
        assertTrue(CurrentWeight.isValidWeight(validDoubleCurrentWeight));
        assertTrue(CurrentWeight.isValidWeight(validCurrentWeight));
    }


}
