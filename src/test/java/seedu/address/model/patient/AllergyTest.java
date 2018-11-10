package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;

public class AllergyTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidAllergy_throwsIllegalArgumentException() {
        String invalidAllergy = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Allergy(invalidAllergy));
    }

    @Test
    public void isValidAllergy() {
        // null Allergy
        Assert.assertThrows(NullPointerException.class, () -> Allergy.isValidAllergy(null));

        // invalid Allergy
        assertFalse(Allergy.isValidAllergy("")); // empty string
        assertFalse(Allergy.isValidAllergy(" ")); // spaces only
        assertFalse(Allergy.isValidAllergy("^")); // only non-alphanumeric characters
        assertFalse(Allergy.isValidAllergy("egg*")); // contains non-alphanumeric characters

        // valid Allergy
        assertTrue(Allergy.isValidAllergy("full milk")); // alphabets only
        assertTrue(Allergy.isValidAllergy("12345")); // numbers only
        assertTrue(Allergy.isValidAllergy("full 2 cream milk")); // alphanumeric characters
        assertTrue(Allergy.isValidAllergy("EGG Egg egg")); // with capital letters
        assertTrue(Allergy.isValidAllergy("Any food contains beans")); // long names
    }

}
