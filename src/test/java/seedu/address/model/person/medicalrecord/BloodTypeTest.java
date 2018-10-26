package seedu.address.model.person.medicalrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BloodTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidBloodType_throwsIllegalArgumentException() {
        String invalidBloodType = "C-";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    public void isValidBloodType() {
        // null blood type
        Assert.assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // blank space
        assertFalse(BloodType.isValidBloodType(" ")); // spaces only

        // invalid blood type
        assertFalse(BloodType.isValidBloodType("A"));
        assertFalse(BloodType.isValidBloodType("B"));
        assertFalse(BloodType.isValidBloodType("O"));
        assertFalse(BloodType.isValidBloodType("AB"));
        assertFalse(BloodType.isValidBloodType("C-"));
        assertFalse(BloodType.isValidBloodType("C+"));
        assertFalse(BloodType.isValidBloodType("ZZZ"));

        // valid blood type
        assertTrue(BloodType.isValidBloodType("A+"));
        assertTrue(BloodType.isValidBloodType("A-"));
        assertTrue(BloodType.isValidBloodType("B+"));
        assertTrue(BloodType.isValidBloodType("B-"));
        assertTrue(BloodType.isValidBloodType("O+"));
        assertTrue(BloodType.isValidBloodType("O-"));
        assertTrue(BloodType.isValidBloodType("AB+"));
        assertTrue(BloodType.isValidBloodType("AB-"));

    }
}
