package seedu.inventory.model.purchaseorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;


public class RequiredDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RequiredDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new RequiredDate(invalidDate));
    }

    @Test
    public void constructor_validDate() {
        assertNotNull(new RequiredDate("2019-12-31"));
    }

    @Test
    public void isValidDate() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> RequiredDate.isValidDate(null));

        // invalid RequiredDate numbers
        assertFalse(RequiredDate.isValidDate("")); // empty string
        assertFalse(RequiredDate.isValidDate(" ")); // spaces only
        assertFalse(RequiredDate.isValidDate("Date")); // non-numeric
        assertFalse(RequiredDate.isValidDate("12-10-2018")); // dd-MM-yyyy
        assertFalse(RequiredDate.isValidDate("2018/12/12")); // yyyy/MM/dd
        assertFalse(RequiredDate.isValidDate("2019-2-2")); // MM and dd missing 0 in front
        assertFalse(RequiredDate.isValidDate("2019-02-31")); // Missing date in calendar
        assertFalse(RequiredDate.isValidDate("1995-03-21")); // Old date

        // valid RequiredDate numbers
        assertTrue(RequiredDate.isValidDate("2018-12-12"));
        assertTrue(RequiredDate.isValidDate("2020-02-29")); //leap year

    }


    @Test
    public void testToString() {
        RequiredDate rDate = new RequiredDate("2018-12-31");
        String expected = "2018-12-31";
        assertEquals(expected, rDate.toString());
    }

    @Test
    public void testEqualsSymmetric() {
        RequiredDate d1 = new RequiredDate("2018-12-31"); // equals and hashCode check name field value
        RequiredDate d2 = new RequiredDate("2018-12-31");
        assertTrue(d1.equals(d1) && d1.equals(d1));
        assertTrue(d1.hashCode() == d2.hashCode());
    }

}
