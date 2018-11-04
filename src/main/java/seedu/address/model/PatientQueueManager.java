package seedu.address.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Patient;

/**
 * Represents the current patient queue during runtime.
 */
public class PatientQueueManager implements PatientQueue {
    private static final Logger logger = LogsCenter.getLogger(PatientQueueManager.class);

    private LinkedList<Patient> patientQueue = new LinkedList<>();

    public PatientQueueManager() {}

    /**
     * Create a new Patient Queue Manager with {@code patientQueueToCopy}
     */
    public PatientQueueManager(List<Patient> patientQueueToCopy) {
        this.patientQueue = (LinkedList<Patient>) patientQueueToCopy;
    }

    @Override
    public String displayQueue() {
        AtomicInteger position = new AtomicInteger(1);
        StringBuilder sb = new StringBuilder();
        AtomicInteger num = new AtomicInteger(0);
        sb.append(getQueueLength()).append(" Patient(s) in queue: ");
        patientQueue.forEach(x -> sb.append("\n").append(num.incrementAndGet())
                                    .append(". ").append(x.toNameAndIc()));
        return sb.toString();
    }

    @Override
    public int enqueue(Patient patient) {
        patientQueue.add(patient);
        patient.joinQueue();
        return getQueueLength();
    }

    @Override
    public void addAtIndex(Patient patient, int index) {
        patientQueue.add(index, patient);
    }

    @Override
    public Patient removePatient(Patient patient) {
        patientQueue.remove(patient);
        patient.leaveQueue();
        return patient;
    }

    @Override
    public Patient dequeue() {
        return patientQueue.removeFirst();
    }

    @Override
    public Patient removeAtIndex(int index) {
        Patient patient = patientQueue.remove(index);
        patient.leaveQueue();
        return patient;
    }

    @Override
    public int getQueueLength() {
        return patientQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return patientQueue.isEmpty();
    }

    @Override
    public boolean contains(Patient patient) {
        return patientQueue.contains(patient);
    }

    @Override
    public List<Patient> getPatientsAsList() {
        return patientQueue;
    }

    @Override
    public void clear() {
        patientQueue.clear();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof PatientQueueManager)) {
            return false;
        }

        // state check
        PatientQueueManager other = (PatientQueueManager) obj;

        return this.patientQueue.equals(other.patientQueue);
    }
}
