package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.QueueCommandTestUtil.assertUniqueFileInFilteredFileList;
import static seedu.address.logic.commands.QueueCommandTestUtil.fileCleanUp;
import static seedu.address.logic.commands.QueueCommandTestUtil.generateFileName;
import static seedu.address.logic.commands.QueueCommandTestUtil.generateServedPatientList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CurrentPatient;
import seedu.address.testutil.TypicalPersons;

public class MedicalCertificateCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private PatientQueue patientQueue;
    private CurrentPatient currentPatient;
    private ServedPatientList servedPatientList;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        patientQueue = new PatientQueueManager();
        currentPatient = new CurrentPatient();
        commandHistory = new CommandHistory();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        servedPatientList = generateServedPatientList(TypicalPersons.ALICE, TypicalPersons.BOB);
        servedPatientList.selectServedPatient(INDEX_FIRST_PERSON).addMcContent("4");
    }

    @Test
    public void execute_validServedPatient_mcMessageSuccess() throws Exception {
        String expectedMessage = MedicalCertificateCommand.MESSAGE_GENERATE_MC_SUCCESS;
        MedicalCertificateCommand mcCommand = new MedicalCertificateCommand(INDEX_FIRST_PERSON);
        CommandResult commandResult = mcCommand.execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
        File file = mcCommand.getMc().getFile();
        fileCleanUp(file);
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_servedPatientListEmpty_commandExceptionThrown() throws Exception {
        thrown.expect(CommandException.class);
        servedPatientList = generateServedPatientList();
        new MedicalCertificateCommand(INDEX_FIRST_PERSON).execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
    }

    @Test
    public void execute_mcFileName_mcGenerationSuccess() throws Exception {
        MedicalCertificateCommand mcCommand = new MedicalCertificateCommand(INDEX_FIRST_PERSON);
        mcCommand.execute(model, patientQueue, currentPatient, servedPatientList, commandHistory);
        String fileType = mcCommand.getMc().FILE_TYPE;
        String fileName = generateFileName(fileType, TypicalPersons.ALICE);
        assertUniqueFileInFilteredFileList(fileName);
    }

    @Test
    public void equals() {
        MedicalCertificateCommand mcAliceCommand = new MedicalCertificateCommand(INDEX_FIRST_PERSON);
        MedicalCertificateCommand mcBobCommand = new MedicalCertificateCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(mcAliceCommand.equals(mcAliceCommand));

        // same values -> returns true
        MedicalCertificateCommand mcAliceCommandCopy = new MedicalCertificateCommand(INDEX_FIRST_PERSON);
        assertTrue(mcAliceCommand.equals(mcAliceCommandCopy));

        // different types -> returns false
        assertFalse(mcAliceCommand.equals(1));

        // null -> returns false
        assertFalse(mcAliceCommand.equals(null));

        // different patient -> returns false
        assertFalse(mcAliceCommand.equals(mcBobCommand));
    }
}
