package seedu.address.model;

import java.util.List;

import seedu.address.model.person.Patient;

/**
 * The API of the PatientQueue component.
 */
public interface PatientQueue {

    /**
     * Displays the queue.
     * @return String representation of the current queue.
     */
    String displayQueue();

    /**
     * Enqueue a patient.
     * @return Position of enqueued patient in the queue.
     */
    int enqueue(Patient patient);

    /**
     * Add a patient to the queue at a specified index.
     */
    void addAtIndex(Patient patient, int index);

    /**
     * Delete a patient in the queue with a specified patient.
     * @return Patient object removed.
     */
    Patient removePatient(Patient patient);

    /**
     * Dequeue a patient.
     *
     * @return Patient object removed from front of the queue.
     */
    Patient dequeue();

    /**
     * Removes a patient from the queue at a specified index.
     * @return Patient object removed at specified index.
     */
    Patient removeAtIndex(int index);

    /**
     * @return Length of the current queue.
     */
    int getQueueLength();

    /**
     * Checks whether the queue is empty.
     * @return true if queue is empty.
     */
    boolean isEmpty();

    /**
     * Checks whether queue contains specified patient.
     * @return true if queue contains patient.
     */
    boolean contains(Patient patient);

    /**
     * Returns the PatientQueue as a List object
     * @return List object of Patients
     */
    List<Patient> getPatientsAsList();

    /**
     * Clears the PatientQueue
     */
    void clear();
}
