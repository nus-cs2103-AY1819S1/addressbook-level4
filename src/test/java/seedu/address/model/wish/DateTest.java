package seedu.address.model.wish;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date((String) null));
        Assert.assertThrows(NullPointerException.class, () -> new Date((Date) null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidName));
    }

    @Test
    public void copyConstructor_success() {
        Date date = new Date("10/23/2020");
        Date copy = new Date(date);
        assertEquals(date, copy);
    }

    @Test
    public void isValidDate() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid date
        assertFalse(Date.isValidDate("32/11/2018")); // invalid day
        assertFalse(Date.isValidDate("01/13/2018")); // invalid month
        assertFalse(Date.isValidDate("")); // empty date string
        assertFalse(Date.isValidDate("email@email.com")); // non date

        // valid
        assertTrue(Date.isValidDate("28/10/2019"));
        assertTrue(Date.isValidDate("2/2/2018"));
        assertTrue(Date.isValidDate("03/2/2018"));
        assertTrue(Date.isValidDate("2/09/2018"));
    }

    @Test(expected = java.text.ParseException.class)
    public void isFutureDate() throws java.text.ParseException {
        // future date -> true
        assertTrue(Date.isFutureDate("29/10/9999"));
        // past date -> false
        assertFalse(Date.isFutureDate("29/10/0001"));

        // invalid parse
        assertTrue(Date.isFutureDate("30/02/9999"));
    }

    @Test
    public void equals() {
        Date date1 = new Date(VALID_DATE_1);
        Date date2 = new Date(VALID_DATE_2);

        // same object
        assertTrue(date1.equals(date1));

        // null -> returns false
        assertFalse(date1.equals(null));

        // different type -> returns false
        assertFalse(date1.equals(1));

        // different date -> returns false
        assertFalse(date1.equals(date2));
    }

    @Test
    public void getNonNullDateObject() {
        assertNotNull(new Date(VALID_DATE_2).getDateObject());
    }

    @Test
    public void hashCodeTest() {
        assertTrue(new Date(VALID_DATE_1).hashCode()
                == new Date(VALID_DATE_1).hashCode());
    }

    @Test
    public void toStringTest() {
        Date date1 = new Date(VALID_DATE_1);
        assertTrue(date1.toString().equals(VALID_DATE_1));
    }

}
