package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Test;

import seedu.address.model.person.Person;

public class PatientQueueTest {
    private PatientQueue<Person> patientQueue = new PatientQueue<>();
    @Test
    public void hasPatient_patientNotInQueue_returnsFalse() {
        assertFalse(patientQueue.hasPatient());
    }
    @Test
    public void hasPatient_patientInQueue_returnTrue() {
        patientQueue.add(ALICE);
        assertTrue(patientQueue.hasPatient());
    }
}
