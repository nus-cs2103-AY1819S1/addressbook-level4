package seedu.address.model.patientqueue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE_AS_PATIENT;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;

public class MainQueueTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPatient_noPatientInMainQueue_returnsFalse() {
        assertFalse(modelManager.hasPatientInMainQueue());
    }

    @Test
    public void hasPatient_patientInMainQueue_returnTrue() throws CommandException {
        modelManager.enqueue(ALICE_AS_PATIENT);
        assertTrue(modelManager.hasPatientInMainQueue());
    }
}
