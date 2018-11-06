package seedu.address.logic.commands;

import static seedu.address.logic.commands.DocumentContentDisplayCommand.MESSAGE_NO_CURRENT_PATIENT;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.ServedPatient;

public class DocumentContentDisplayCommandTest {
    private static final String MC_CONTENT = "mc content";
    private static final String NOTE_CONTENT = "note content";
    private static final String REFERRAL_CONTENT = "referral content";

    private Model model = new ModelManager();
    private PatientQueue patientQueue;
    private CurrentPatient currentPatient;
    private ServedPatientList servedPatientList;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        patientQueue = new PatientQueueManager();
        servedPatientList = new ServedPatientListManager();
        commandHistory = new CommandHistory();
        model.addPerson(AMY);
    }

    @Test
    public void execute_existingCurrentPatient_success() {
        currentPatient = new CurrentPatient(new ServedPatient(AMY));
        currentPatient.addMcContent(MC_CONTENT);
        currentPatient.addNoteContent(NOTE_CONTENT);
        currentPatient.addReferralContent(REFERRAL_CONTENT);
        String expectedMessage = currentPatient.toNameAndIc() + currentPatient.toDocumentInformation();

        assertCommandSuccess(new DocumentContentDisplayCommand(), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, patientQueue, currentPatient,
                servedPatientList, expectedMessage);
    }

    @Test
    public void execute_emptyCurrentPatient_failure() {
        currentPatient = new CurrentPatient();
        assertCommandFailure(new DocumentContentDisplayCommand(), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, MESSAGE_NO_CURRENT_PATIENT);
    }
}
