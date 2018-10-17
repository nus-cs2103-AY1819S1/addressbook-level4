package seedu.inventory.model.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class StaffNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StaffName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StaffName(invalidName));
    }

    @Test
    public void isValidName() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> StaffName.isValidName(null));

        // invalid name
        assertFalse(StaffName.isValidName(""));
        assertFalse(StaffName.isValidName(" "));
        assertFalse(StaffName.isValidName("*"));
        assertFalse(StaffName.isValidName("john..."));

        // valid name
        assertTrue(StaffName.isValidName("john doe"));
        assertTrue(StaffName.isValidName("Alexander the 2nd"));
        assertTrue(StaffName.isValidName("23123"));
        assertTrue(StaffName.isValidName("Alex Da Myth"));
        assertTrue(StaffName.isValidName("Mohammed Salah bin Abdullah Salleh"));
    }
}
