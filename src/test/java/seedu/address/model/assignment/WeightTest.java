package seedu.address.model.assignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class WeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void constructor_invalidWeight_throwsIllegalArgumentException() {
        String invalidWeight = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Weight(invalidWeight));
    }

    @Test
    public void isValidWeight() {
        // null weight
        Assert.assertThrows(NullPointerException.class, () -> Weight.isValid(null));

        // invalid weight
        assertFalse(Weight.isValid("")); // empty string
        assertFalse(Weight.isValid(" ")); // spaces only
        assertFalse(Weight.isValid("-12asdf")); // non number at the back
        assertFalse(Weight.isValid("asdf-12")); // non number at the front
        assertFalse(Weight.isValid("-1asdf2")); // non number at the middle

        // valid weight
        assertTrue(Weight.isValid("12"));
        assertTrue(Weight.isValid("-12")); // negative
        assertTrue(Weight.isValid("12.5")); // non integer
    }

    @Test
    public void weightComparison() {
        Weight weight1 = new Weight("-13");
        Weight weight2 = new Weight("-0.5");
        Weight weight3 = new Weight("3");

        assertEquals(weight1.compareTo(weight2), -1);
        assertEquals(weight1.compareTo(weight3), -1);
        assertEquals(weight2.compareTo(weight3), -1);
        assertEquals(weight1.compareTo(weight1), 0);
        assertEquals(weight2.compareTo(weight2), 0);
        assertEquals(weight3.compareTo(weight3), 0);
    }

    @Test
    public void weightValue() {
        Weight weight1 = new Weight("-13");
        Weight weight2 = new Weight("-0.5");
        Weight weight3 = new Weight("3");

        assertEquals(weight1.getValue(), Double.valueOf(-13));
        assertEquals(weight2.getValue(), Double.valueOf(-0.5));
        assertEquals(weight3.getValue(), Double.valueOf(3));
    }
}
