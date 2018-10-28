package seedu.clinicio.model.patientqueue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.testutil.TypicalPersons.ALICE_AS_PATIENT;

import org.junit.Test;

import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.ModelManager;

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
