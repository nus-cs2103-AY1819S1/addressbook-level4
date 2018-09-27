package seedu.address.model;

import seedu.address.commons.core.LogsCenter;

import java.util.LinkedList;
import java.util.logging.Logger;

public class PatientQueueManager implements PatientQueue {
    private static final Logger logger = LogsCenter.getLogger(PatientQueueManager.class);

    private LinkedList<String> patientQueue = new LinkedList<>();

    @Override
    public String displayQueue() {
        StringBuilder sb = new StringBuilder();
        sb.append("Queue: ");
        patientQueue.stream()
                    .forEach(x -> sb.append(x + ", "));
        return sb.toString();
    }

    @Override
    public int enqueue(String patient) {
        patientQueue.add(patient);
        return patientQueue.size() - 1;
    }

    @Override
    public void addAtIndex(String patient, int index) {
        patientQueue.add(index, patient);
    }

    @Override
    public String removePatient(String patient) {
        patientQueue.remove(patient);
        return patient;
    }

    @Override
    public String dequeue() {
        return patientQueue.removeFirst();
    }

    @Override
    public String removeAtIndex(int index) {
        return patientQueue.remove(index);
    }

    @Override
    public int getQueueLength() {
        return patientQueue.size();
    }

}
