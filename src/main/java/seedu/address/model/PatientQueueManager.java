package seedu.address.model;

import java.util.LinkedList;
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

    @Override
    public String displayQueue() {
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
        return getQueueLength();
    }

    @Override
    public void addAtIndex(Patient patient, int index) {
        patientQueue.add(index, patient);
    }

    @Override
    public Patient removePatient(Patient patient) {
        patientQueue.remove(patient);
        return patient;
    }

    @Override
    public Patient dequeue() {
        return patientQueue.removeFirst();
    }

    @Override
    public Patient removeAtIndex(int index) {
        return patientQueue.remove(index);
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
}
