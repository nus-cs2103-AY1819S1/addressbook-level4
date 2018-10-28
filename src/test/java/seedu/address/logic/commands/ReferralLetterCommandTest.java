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
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CurrentPatient;
import seedu.address.testutil.TypicalPersons;

public class ReferralLetterCommandTest {
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
    }

    @Test
    public void execute_validServedPatient_referralMessageSuccess() throws Exception {
        String expectedMessage = ReferralLetterCommand.MESSAGE_GENERATE_REFERRAL_SUCCESS;
        ReferralLetterCommand referralLetterCommand = new ReferralLetterCommand(INDEX_FIRST_PERSON);
        CommandResult commandResult = referralLetterCommand.execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
        File file = referralLetterCommand.getRl().getFile();
        fileCleanUp(file);
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_servedPatientListEmpty_commandExceptionThrown() throws Exception {
        thrown.expect(CommandException.class);
        servedPatientList = generateServedPatientList();
        new ReferralLetterCommand(INDEX_FIRST_PERSON).execute(model, patientQueue,
                currentPatient, servedPatientList, commandHistory);
    }

    @Test
    public void execute_mcFileName_mcGenerationSuccess() throws Exception {
        ReferralLetterCommand referralLetterCommand = new ReferralLetterCommand(INDEX_FIRST_PERSON);
        referralLetterCommand.execute(model, patientQueue, currentPatient, servedPatientList, commandHistory);
        String fileType = referralLetterCommand.getRl().FILE_TYPE;
        String fileName = generateFileName(fileType, TypicalPersons.ALICE);
        assertUniqueFileInFilteredFileList(fileName);
    }

    @Test
    public void equals() {
        ReferralLetterCommand rlAliceCommand = new ReferralLetterCommand(INDEX_FIRST_PERSON);
        ReferralLetterCommand rlBobCommand = new ReferralLetterCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(rlAliceCommand.equals(rlAliceCommand));

        // same values -> returns true
        ReferralLetterCommand rlAliceCommandCopy = new ReferralLetterCommand(INDEX_FIRST_PERSON);
        assertTrue(rlAliceCommand.equals(rlAliceCommandCopy));

        // different types -> returns false
        assertFalse(rlAliceCommand.equals(1));

        // null -> returns false
        assertFalse(rlAliceCommand.equals(null));

        // different patient -> returns false
        assertFalse(rlAliceCommand.equals(rlBobCommand));
    }
}
