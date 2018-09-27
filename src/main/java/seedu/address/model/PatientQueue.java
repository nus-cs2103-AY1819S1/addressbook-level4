package seedu.address.model;

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
    int enqueue(String patientName);

    /**
     * Add a patient to the queue at a specified index.
     */
    void addAtIndex(String patientName, int index);

    /**
     * Delete a patient in the queue with a specified name.
     * @return String representation of the removed patient.
     */
    String removePatient(String patientName);

    /**
     * Dequeue a patient.
     * @return String representation of the patient dequeued.
     */
    String dequeue();

    /**
     * Removes a patient from the queue at a specified index.
     * @return String representation of the patient removed.
     */
    String removeAtIndex(int index);

    /**
     * @return Length of the current queue.
     */
    int getQueueLength();
}
