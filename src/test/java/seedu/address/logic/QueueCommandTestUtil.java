package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.QueueCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

public class QueueCommandTestUtil {

    /**
     * Generates a patient queue from given patients.
     * @param patients to be added into patient queue.
     * @return generated patient queue.
     */
    public static PatientQueue generatePatientQueue(Patient... patients) {
        PatientQueue patientQueue = new PatientQueueManager();
        for(Patient p: patients) {
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
        for(Patient patient: patients) {
            ServedPatient servedPatient = new ServedPatient(patient);
            servedPatientList.addServedPatient(servedPatient);
        }
        return servedPatientList;
    }

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
