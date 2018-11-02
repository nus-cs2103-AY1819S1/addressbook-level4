package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MedicalHistoryTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addAllergy() {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.addAllergy("Nuts");

        // Check addition of allergies
        assertTrue(medicalHistory.getAllergies().get(0).equals("Nuts"));
        assertFalse(medicalHistory.getAllergies().get(0).equals("Diary"));

        medicalHistory.addAllergy("Diary");

        // Check if new allergy is added to the back of list
        assertFalse(medicalHistory.getAllergies().get(0).equals("Diary"));
        assertTrue(medicalHistory.getAllergies().get(1).equals("Diary"));
    }

    @Test
    public void addCondition() {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.addCondition("Asthma");

        // Check addition of condition
        assertTrue(medicalHistory.getConditions().get(0).equals("Asthma"));
        assertFalse(medicalHistory.getConditions().get(0).equals("Sleep Walking"));

        medicalHistory.addCondition("Sleep Walking");

        // Check if new condition is added to back of list
        assertFalse(medicalHistory.getConditions().get(0).equals("Sleep Walking"));
        assertTrue(medicalHistory.getConditions().get(1).equals("Sleep Walking"));
    }

    @Test
    public void equals() {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.addAllergy("Nuts");
        medicalHistory.addCondition("Asthma");

        MedicalHistory sameMedicalHistory = new MedicalHistory();
        sameMedicalHistory.setAllergies(medicalHistory.getAllergies());
        sameMedicalHistory.setConditions(medicalHistory.getConditions());

        MedicalHistory differentMedicalHistory = new MedicalHistory();
        differentMedicalHistory.addAllergy("Chocolate");
        differentMedicalHistory.addCondition("Dust");

        assertTrue(medicalHistory.equals(medicalHistory));
        assertTrue(medicalHistory.equals(sameMedicalHistory));
        assertFalse(medicalHistory.equals(differentMedicalHistory));

    }
}
