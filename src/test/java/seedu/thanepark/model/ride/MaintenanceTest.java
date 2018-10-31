package seedu.thanepark.model.ride;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.thanepark.testutil.Assert;

public class MaintenanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Maintenance(null));
    }

    @Test
    public void constructor_invalidMaintenance_throwsIllegalArgumentException() {
        String invalidMaintenance = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Maintenance(invalidMaintenance));
    }

    @Test
    public void equal() {
        Maintenance firstMaintenance = new Maintenance("100");
        Maintenance secondMaintenance = new Maintenance(100);
        Maintenance thirdMaintenance = new Maintenance(50);

        assertTrue(firstMaintenance.equals(firstMaintenance));

        // same value using different methods -> returns true
        assertTrue(firstMaintenance.equals(secondMaintenance));

        // null -> returns false
        assertFalse(firstMaintenance.equals(null));

        // different types -> returns false
        assertFalse(firstMaintenance.equals(1));

        // different values -> returns false
        assertFalse(firstMaintenance.equals(thirdMaintenance));
    }

    @Test
    public void isValidMaintenance() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Maintenance.isValidMaintenance(null));

        // invalid numbers
        assertFalse(Maintenance.isValidMaintenance("")); // empty string
        assertFalse(Maintenance.isValidMaintenance(" ")); // spaces only
        assertFalse(Maintenance.isValidMaintenance("phone")); // non-numeric
        assertFalse(Maintenance.isValidMaintenance("9011p041")); // alphabets within digits
        assertFalse(Maintenance.isValidMaintenance("9312 1534")); // spaces within digits
        assertFalse(Maintenance.isValidMaintenance("124293842033123")); // long phone numbers

        // valid numbers
        assertTrue(Maintenance.isValidMaintenance("1")); // exactly 1 numbers
        assertTrue(Maintenance.isValidMaintenance("93121534"));
    }

    @Test
    public void setsCorrectValue() {
        Maintenance firstMaintenance = new Maintenance("100");
        Maintenance secondMaintenance = new Maintenance("50");
        firstMaintenance.setValue(50);
        assertEquals(firstMaintenance, secondMaintenance);
    }
}
