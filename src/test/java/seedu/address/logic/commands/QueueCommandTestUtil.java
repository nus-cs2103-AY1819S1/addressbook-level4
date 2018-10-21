package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

/**
 * Contains helper methods for testing QueueCommands.
 */
public class QueueCommandTestUtil {

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
            throw new AssertionError("Execution of command should not fail.", ce);
        }

    }

}
