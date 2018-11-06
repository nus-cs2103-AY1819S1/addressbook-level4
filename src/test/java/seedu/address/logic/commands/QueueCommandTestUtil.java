package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.model.document.Document.DIRECTORY_PATH;
import static seedu.address.model.document.Document.FILE_NAME_DELIMITER;
import static seedu.address.testutil.TypicalPersons.getSamplePersonsArrayList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

/**
 * Contains helper methods for testing QueueCommands.
 */
public class QueueCommandTestUtil {

    private static final String VALID_MC_CONTENT = "5";
    private static final String VALID_REFERRAL_LETTER_CONTENT = "Go to NUH pls";

    /**
     * Generates a patient queue from given patients.
     * @param patients to be added into patient queue.
     * @return generated patient queue.
     */
    public static PatientQueue generatePatientQueue(Patient... patients) {
        PatientQueue patientQueue = new PatientQueueManager();
        for (Patient p: patients) {
            patientQueue.enqueue(p);
        }
        return patientQueue;
    }

    /**
     * Generates a served patient list from given patients.
     * @param patients to be added into patient queue.
     * @return generated served patient list
     */
    public static ServedPatientList generateServedPatientList(Patient... patients) {
        ServedPatientList servedPatientList = new ServedPatientListManager();
        for (Patient patient: patients) {
            ServedPatient servedPatient = new ServedPatient(patient);
            servedPatient.addMcContent(VALID_MC_CONTENT);
            servedPatient.addReferralContent(VALID_REFERRAL_LETTER_CONTENT);
            servedPatientList.addServedPatient(servedPatient);
        }
        return servedPatientList;
    }

    /**
     * Executes the given {@code queueCommand}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualPatientQueue} matches {@code expectedPatientQueue} <br>
     * - the {@code actualCurrentPatient} matches {@code expectedCurrentPatient} <br>
     * - the {@code actualServedPatientList} matches {@code actualServedPatientList} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(QueueCommand queueCommand, CommandHistory actualCommandHistory,
                                       PatientQueue actualPatientQueue, CurrentPatient actualCurrentPatient,
                                       ServedPatientList actualServedPatientList, Model model,
                                       PatientQueue expectedPatientQueue, CurrentPatient expectedCurrentPatient,
                                       ServedPatientList expectedServedPatientList, String expectedMessage) {

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            CommandResult result = queueCommand.execute(model, actualPatientQueue, actualCurrentPatient,
                    actualServedPatientList, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedPatientQueue, actualPatientQueue);
            assertEquals(expectedCurrentPatient, actualCurrentPatient);
            assertEquals(expectedServedPatientList, actualServedPatientList);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            System.out.println(ce.getMessage());
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code queueCommand}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualPatientQueue} matches {@code expectedPatientQueue} <br>
     * - the {@code actualCurrentPatient} matches {@code expectedCurrentPatient} <br>
     * - the {@code actualServedPatientList} matches {@code actualServedPatientList} <br>
     * - the {@code actualCommandHistory} remains unchanged <br>
     * - the {@code actualModel} matches {@code expectedModel}.
     */
    public static void assertCommandSuccess(QueueCommand queueCommand, CommandHistory actualCommandHistory,
                                            PatientQueue actualPatientQueue, CurrentPatient actualCurrentPatient,
                                            ServedPatientList actualServedPatientList, Model actualModel,
                                            PatientQueue expectedPatientQueue, CurrentPatient expectedCurrentPatient,
                                            ServedPatientList expectedServedPatientList, Model expectedModel,
                                            String expectedMessage) {

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            CommandResult result = queueCommand.execute(actualModel, actualPatientQueue, actualCurrentPatient,
                    actualServedPatientList, actualCommandHistory);


            System.out.println(expectedModel.getFilteredMedicineList());
            System.out.println(actualModel.getFilteredMedicineList());

            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedPatientQueue, actualPatientQueue);
            assertEquals(expectedCurrentPatient, actualCurrentPatient);
            assertEquals(expectedServedPatientList, actualServedPatientList);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            System.out.println(ce.getMessage());
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code queueCommand}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the {@code patientQueue} remains unchanged <br>
     * - the {@code currentPatient} remains unchanged <br>
     * - the {@code servedPatientList} remains unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(QueueCommand queueCommand, CommandHistory actualCommandHistory,
                                             PatientQueue patientQueue, CurrentPatient currentPatient,
                                            ServedPatientList servedPatientList, Model model, String expectedMessage) {
        PatientQueue expectedPatientQueue = new PatientQueueManager((patientQueue.getPatientsAsList()));
        CurrentPatient expectedCurrentPatient = new CurrentPatient(currentPatient.getServedPatient());
        ServedPatientList expectedServedPatientList = new ServedPatientListManager(
                servedPatientList.getPatientsAsList());
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        List<Patient> expectedFilteredPatientList = new ArrayList<>(model.getFilteredPersonList());
        List<Medicine> expectedFilteredMedicineList = new ArrayList<>(model.getFilteredMedicineList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            queueCommand.execute(model, patientQueue, currentPatient, servedPatientList, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedPatientQueue, patientQueue);
            assertEquals(expectedCurrentPatient, currentPatient);
            assertEquals(expectedServedPatientList, servedPatientList);
            assertEquals(expectedAddressBook, model.getAddressBook());
            assertEquals(expectedFilteredPatientList, model.getFilteredPersonList());
            assertEquals(expectedFilteredMedicineList, model.getFilteredMedicineList());
            assertEquals(actualCommandHistory, expectedCommandHistory);

        }
    }

    /**
     * Executes the given {@code queueCommand}, confirms that there exists a file in the generated
     * documents directory
     * @param fileName the name of the file which presence is being tested in the generated document directory
     */
    public static void assertUniqueFileInFilteredFileList(String fileName) {
        File dir = new File(DIRECTORY_PATH);
        File[] matches = dir.listFiles((dir1, name) -> name.startsWith(fileName));
        assertTrue("There should be exactly 1 file in the directory!", matches.length == 1);
        fileCleanUp(matches[0]);
    }

    /**
     * Generates the fileName given the type of file and the patient to generate the file for
     * @param fileType the type of file which presence is being tested in the generated document directory
     * @param patient the patient for whom the file is being generated for
     */
    public static String generateFileName(String fileType, Patient patient) {
        return (fileType + FILE_NAME_DELIMITER + "For" + FILE_NAME_DELIMITER + patient.toNameAndIc()
                .replace("[", "_").replace("]", ""))
                .replaceAll("\\s", "_")
                .replaceAll("(_)+", "_");
    }

    /**
     * Cleans up the files that have been created for the tests
     * @param file the file to be cleaned up
     */
    public static void fileCleanUp(File file) {
        file.delete();
    }

    public static ServedPatientList getSampleServedPatientsList() {
        ServedPatientList servedPatientList = new ServedPatientListManager();
        getSamplePersonsArrayList().stream().forEach(patient -> servedPatientList.addServedPatient(
                new ServedPatient(patient)));

        return servedPatientList;
    }
}
