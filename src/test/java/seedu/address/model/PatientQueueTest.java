package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Test;

import seedu.address.model.person.Person;

public class PatientQueueTest {
    private ModelManager modelManager = new ModelManager();
    @Test
    public void hasPatient_patientNotInQueue_returnsFalse() {
        assertFalse(modelManager.hasPatientInPatientQueue());
    }
    @Test
    public void hasPatient_patientInQueue_returnTrue() {
        modelManager.enqueue(ALICE);
        assertTrue(modelManager.hasPatientInPatientQueue());
    }
}
