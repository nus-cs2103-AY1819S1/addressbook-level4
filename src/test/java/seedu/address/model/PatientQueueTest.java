package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE_AS_PATIENT;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class PatientQueueTest {
    private ModelManager modelManager = new ModelManager();
    @Test
    public void hasPatient_patientNotInQueue_returnsFalse() {
        assertFalse(modelManager.hasPatientInPatientQueue());
    }
    @Test
    public void hasPatient_patientInQueue_returnTrue() throws CommandException {
        modelManager.enqueue(ALICE_AS_PATIENT);
        assertTrue(modelManager.hasPatientInPatientQueue());
    }
}
