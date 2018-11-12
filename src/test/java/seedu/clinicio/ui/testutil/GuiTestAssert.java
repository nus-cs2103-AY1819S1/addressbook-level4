package seedu.clinicio.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.MedicineCardHandle;
import guitests.guihandles.MedicineListPanelHandle;
import guitests.guihandles.PatientCardHandle;
import guitests.guihandles.PatientListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;

import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.patient.Patient;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PatientCardHandle expectedCard, PatientCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        //assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPatient}.
     */
    public static void assertCardDisplaysPatient(Patient expectedPatient, PatientCardHandle actualCard) {
        assertEquals(expectedPatient.getName().fullName, actualCard.getName());
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PatientListPanelHandle patientListPanelHandle, Patient... patients) {
        for (int i = 0; i < patients.length; i++) {
            patientListPanelHandle.navigateToCard(i);
            assertCardDisplaysPatient(patients[i], patientListPanelHandle.getPatientCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PatientListPanelHandle patientListPanelHandle, List<Patient> patients) {
        assertListMatching(patientListPanelHandle, patients.toArray(new Patient[0]));
    }

    /**
     * Asserts the size of the list in {@code patientListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PatientListPanelHandle patientListPanelHandle, int size) {
        int numberOfPeople = patientListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertMedicineCardEquals(MedicineCardHandle expectedCard, MedicineCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getMedicineName(), actualCard.getMedicineName());
        assertEquals(expectedCard.getMedicineType(), actualCard.getMedicineType());
        assertEquals(expectedCard.getEffectiveDosage(), actualCard.getEffectiveDosage());
        assertEquals(expectedCard.getLethalDosage(), actualCard.getLethalDosage());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
        assertEquals(expectedCard.getQuantity(), actualCard.getQuantity());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedMedicine}.
     */
    public static void assertCardDisplaysMedicine(Medicine expectedMedicine, MedicineCardHandle actualCard) {
        assertEquals(expectedMedicine.getMedicineName().medicineName, actualCard.getMedicineName());
        assertEquals(expectedMedicine.getMedicineType().medicineType, actualCard.getMedicineType());
        assertEquals(expectedMedicine.getEffectiveDosage().medicineDosage, actualCard.getEffectiveDosage());
        assertEquals(expectedMedicine.getLethalDosage().medicineDosage, actualCard.getLethalDosage());
        assertEquals(expectedMedicine.getPrice().medicinePrice, actualCard.getPrice());
        assertEquals(expectedMedicine.getQuantity().medicineQuantity, actualCard.getQuantity());
    }

    /**
     * Asserts that the list in {@code medicineListPanelHandle} displays the details of {@code medicines} correctly and
     * in the correct order.
     */
    public static void assertMedicineListMatching(MedicineListPanelHandle medicineListPanelHandle,
                                                  Medicine... medicines) {
        for (int i = 0; i < medicines.length; i++) {
            medicineListPanelHandle.navigateToCard(i);
            assertCardDisplaysMedicine(medicines[i], medicineListPanelHandle.getMedicineCardHandle(i));
        }
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
