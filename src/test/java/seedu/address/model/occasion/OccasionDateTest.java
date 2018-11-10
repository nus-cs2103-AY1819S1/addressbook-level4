package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OccasionDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OccasionDate(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidOccasionDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OccasionDate(invalidOccasionDate));
    }

    @Test
    public void isValidOccasionDate() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> OccasionDate.isValidDate(null));

        // invalid name
        assertFalse(OccasionDate.isValidDate("")); // empty string
        assertFalse(OccasionDate.isValidDate(" ")); // spaces only
        assertFalse(OccasionDate.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(OccasionDate.isValidDate("whattime*")); // contains non-numeric characters
        assertFalse(OccasionDate.isValidDate("2018-12-23*")); // non-existent date
        assertFalse(OccasionDate.isValidDate("2018-02-29*")); // non-existent date
        assertFalse(OccasionDate.isValidDate("2018/02/21")); // incorrect date format
        assertFalse(OccasionDate.isValidDate("2018-12-32")); // non-existent date
        assertFalse(OccasionDate.isValidDate("2018-02-29")); // non-existent date

        // valid name
        assertTrue(OccasionDate.isValidDate("2018-01-01")); // YYYY-MM-DD
        assertTrue(OccasionDate.isValidDate("1000-12-31")); // YYYY-MM-DD

    }
}
