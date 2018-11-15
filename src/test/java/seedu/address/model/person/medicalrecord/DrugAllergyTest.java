package seedu.address.model.person.medicalrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DrugAllergyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DrugAllergy(null));
    }

    @Test
    public void constructor_invalidDrugAllergy_throwsIllegalArgumentException() {
        String invalidDrugAllergy = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DrugAllergy(invalidDrugAllergy));
    }

    @Test
    public void isValidDrugAllergy() {
        // null DrugAllergy
        Assert.assertThrows(NullPointerException.class, () -> DrugAllergy.isValidDrugAllergy(null));

        // invalid DrugAllergy
        assertFalse(DrugAllergy.isValidDrugAllergy(""));
        assertFalse(DrugAllergy.isValidDrugAllergy(" "));
        assertFalse(DrugAllergy.isValidDrugAllergy("**"));
        assertFalse(DrugAllergy.isValidDrugAllergy("()"));
        assertFalse(DrugAllergy.isValidDrugAllergy("dsfvsdf#$%"));


        // valid DrugAllergy
        assertTrue(DrugAllergy.isValidDrugAllergy("Panadol"));
        assertTrue(DrugAllergy.isValidDrugAllergy("Zyrtec"));
        assertTrue(DrugAllergy.isValidDrugAllergy("Antihistamine"));
        assertTrue(DrugAllergy.isValidDrugAllergy("Antibiotic"));
        assertTrue(DrugAllergy.isValidDrugAllergy("Antacid"));
        assertTrue(DrugAllergy.isValidDrugAllergy("Dopamine"));


    }
}
