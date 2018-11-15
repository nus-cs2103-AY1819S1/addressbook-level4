package seedu.address.model.person.medicalrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DiseaseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Disease(null));
    }

    @Test
    public void constructor_invalidDisease_throwsIllegalArgumentException() {
        String invalidDisease = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Disease(invalidDisease));
    }

    @Test
    public void isValidDisease() {
        // null disease
        Assert.assertThrows(NullPointerException.class, () -> Disease.isValidDisease(null));

        // invalid disease
        assertFalse(Disease.isValidDisease(""));
        assertFalse(Disease.isValidDisease(" "));
        assertFalse(Disease.isValidDisease("**"));
        assertFalse(Disease.isValidDisease("()"));
        assertFalse(Disease.isValidDisease("dsfvsdf#$%"));


        // valid disease
        assertTrue(Disease.isValidDisease("Flu"));
        assertTrue(Disease.isValidDisease("Fever"));
        assertTrue(Disease.isValidDisease("Nausea"));
        assertTrue(Disease.isValidDisease("Diarrhea"));
        assertTrue(Disease.isValidDisease("Cancer"));
        assertTrue(Disease.isValidDisease("HIV"));
        assertTrue(Disease.isValidDisease("AIDS"));


    }

}
