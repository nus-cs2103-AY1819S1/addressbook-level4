package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.util.CollectionUtil.isAnyNonNull;
import static seedu.address.logic.commands.DocumentContentAddCommand.DocumentContentDescriptor;
import static seedu.address.logic.commands.DocumentContentAddCommand.MESSAGE_NO_CURRENT_PATIENT;
import static seedu.address.logic.commands.DocumentContentAddCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.QueueCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.ServedPatient;

public class DocumentContentAddCommandTest {
    private static final String MC_CONTENT = "mc content";
    private static final String NOTE_CONTENT = "note content";
    private static final String REFERRAL_CONTENT = "referral content";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private PatientQueue patientQueue;
    private CurrentPatient currentPatient;
    private ServedPatientList servedPatientList;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        patientQueue = new PatientQueueManager();
        currentPatient = new CurrentPatient(new ServedPatient(AMY));
        servedPatientList = new ServedPatientListManager();
        commandHistory = new CommandHistory();
        model.addPerson(AMY);
    }

    @Test
    public void constructor_nullIndex_throwException() {
        thrown.expect(NullPointerException.class);
        new DocumentContentAddCommand(null);
    }

    @Test
    public void execute_allParametersEmptyDocumentContent_success() {
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        expectedCurrentPatient.addMcContent(MC_CONTENT);
        expectedCurrentPatient.addNoteContent(NOTE_CONTENT);
        expectedCurrentPatient.addReferralContent(REFERRAL_CONTENT);


        String expectedMessage = MESSAGE_SUCCESS + expectedCurrentPatient.toNameAndIc()
                + expectedCurrentPatient.toDocumentInformation();

        DocumentContentDescriptor documentContentDescriptor = generateDocumentContentDescriptor(MC_CONTENT,
                NOTE_CONTENT, REFERRAL_CONTENT);


        assertCommandSuccess(new DocumentContentAddCommand(documentContentDescriptor), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient, servedPatientList,
                expectedMessage);
    }

    @Test
    public void execute_allParametersPopulatedDocumentContent_success() {
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        expectedCurrentPatient.addMcContent(MC_CONTENT);
        expectedCurrentPatient.addNoteContent(NOTE_CONTENT);
        expectedCurrentPatient.addReferralContent(REFERRAL_CONTENT);

        String expectedMessage = MESSAGE_SUCCESS + expectedCurrentPatient.toNameAndIc()
                + expectedCurrentPatient.toDocumentInformation();

        //Add document content values
        currentPatient.addMcContent(NOTE_CONTENT);
        currentPatient.addReferralContent(MC_CONTENT);
        currentPatient.addNoteContent(REFERRAL_CONTENT);

        DocumentContentDescriptor documentContentDescriptor = generateDocumentContentDescriptor(MC_CONTENT,
                NOTE_CONTENT, REFERRAL_CONTENT);


        assertCommandSuccess(new DocumentContentAddCommand(documentContentDescriptor), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient, servedPatientList,
                expectedMessage);
    }

    @Test
    public void execute_someParametersEmptyDocumentContent_success() {
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        expectedCurrentPatient.addMcContent(MC_CONTENT);
        expectedCurrentPatient.addReferralContent(REFERRAL_CONTENT);

        String expectedMessage = MESSAGE_SUCCESS + expectedCurrentPatient.toNameAndIc()
                + expectedCurrentPatient.toDocumentInformation();

        DocumentContentDescriptor documentContentDescriptor = generateDocumentContentDescriptor(MC_CONTENT,
                null, REFERRAL_CONTENT);

        assertCommandSuccess(new DocumentContentAddCommand(documentContentDescriptor), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient, servedPatientList,
                expectedMessage);
    }

    @Test
    public void execute_someParametersPopulatedDocumentContent_success() {
        CurrentPatient expectedCurrentPatient = new CurrentPatient(new ServedPatient(AMY));
        expectedCurrentPatient.addMcContent(MC_CONTENT);
        expectedCurrentPatient.addReferralContent(REFERRAL_CONTENT);

        String expectedMessage = MESSAGE_SUCCESS + expectedCurrentPatient.toNameAndIc()
                + expectedCurrentPatient.toDocumentInformation();

        //Add document content values
        currentPatient.addMcContent(NOTE_CONTENT);
        currentPatient.addReferralContent(MC_CONTENT);

        DocumentContentDescriptor documentContentDescriptor = generateDocumentContentDescriptor(MC_CONTENT,
                null, REFERRAL_CONTENT);

        assertCommandSuccess(new DocumentContentAddCommand(documentContentDescriptor), commandHistory, patientQueue,
                currentPatient, servedPatientList, model, patientQueue, expectedCurrentPatient, servedPatientList,
                expectedMessage);
    }

    @Test
    public void execute_noCurrentPatient_failure() {
        currentPatient = new CurrentPatient();
        String expectedMessage = MESSAGE_NO_CURRENT_PATIENT;

        DocumentContentDescriptor documentContentDescriptor = generateDocumentContentDescriptor(MC_CONTENT,
                NOTE_CONTENT, REFERRAL_CONTENT);

        assertCommandFailure(new DocumentContentAddCommand(documentContentDescriptor), commandHistory,
                patientQueue, currentPatient, servedPatientList, model, expectedMessage);
    }

    @Test
    public void equals() {
        final DocumentContentAddCommand standardCommand = new DocumentContentAddCommand(
                generateDocumentContentDescriptor(MC_CONTENT, NOTE_CONTENT, REFERRAL_CONTENT));

        DocumentContentAddCommand commandWithSameValues = new DocumentContentAddCommand(
                generateDocumentContentDescriptor(MC_CONTENT, NOTE_CONTENT, REFERRAL_CONTENT));

        assertTrue(standardCommand.equals(commandWithSameValues));

        assertFalse(standardCommand.equals(null));

        assertFalse(standardCommand.equals(new ClearCommand()));

        assertFalse(standardCommand.equals(new DocumentContentAddCommand(
                generateDocumentContentDescriptor(MC_CONTENT, null, REFERRAL_CONTENT))));

        assertFalse(standardCommand.equals(new DocumentContentAddCommand(
                generateDocumentContentDescriptor(REFERRAL_CONTENT, REFERRAL_CONTENT, REFERRAL_CONTENT))));
    }

    /**
     * Helper method to generate a {@code DocumentContentDescriptor}
     */
    public DocumentContentDescriptor generateDocumentContentDescriptor(String mcContent, String noteContent,
                                                                       String referralContent) {
        //descriptor cannot be all null
        assertTrue(isAnyNonNull(mcContent, noteContent, referralContent));

        DocumentContentDescriptor documentContentDescriptor = new DocumentContentDescriptor();

        if (mcContent != null) {
            documentContentDescriptor.setMcContent(mcContent);
        }

        if (noteContent != null) {
            documentContentDescriptor.setNoteContent(noteContent);
        }

        if (referralContent != null) {
            documentContentDescriptor.setReferralContent(referralContent);
        }

        return documentContentDescriptor;
    }
}
