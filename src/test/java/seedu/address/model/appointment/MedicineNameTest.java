package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MedicineNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicineName(null));
    }

    @Test
    public void constructor_invalidMedicineName_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicineName(invalidAmount));
    }

    @Test
    public void isValidMedicineName() {
        // null medicine name
        Assert.assertThrows(NullPointerException.class, () -> MedicineName.isValidMedicineName(null));

        // invalid medicine name
        assertFalse(MedicineName.isValidMedicineName("")); // empty string
        assertFalse(MedicineName.isValidMedicineName(" ")); // spaces only
        assertFalse(MedicineName.isValidMedicineName("^")); // only non-alphanumeric characters
        assertFalse(MedicineName.isValidMedicineName("peter*")); // contains non-alphanumeric characters

        // valid medicine name
        assertTrue(MedicineName.isValidMedicineName("peter jack")); // alphabets only
        assertTrue(MedicineName.isValidMedicineName("12345")); // numbers only
        assertTrue(MedicineName.isValidMedicineName("peter the 2nd")); // alphanumeric characters
        assertTrue(MedicineName.isValidMedicineName("Capital Tan")); // with capital letters
        assertTrue(MedicineName.isValidMedicineName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void getFullMedicineName() {
        MedicineName medicineName = new MedicineName("Test Test");
        assertSame(medicineName.getFullMedicineName(), "Test Test");
        assertNotSame(medicineName.getFullMedicineName(), "Testing");
    }
}
