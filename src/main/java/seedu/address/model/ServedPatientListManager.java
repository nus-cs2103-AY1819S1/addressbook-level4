package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the current list of served patients during runtime.
 */
public class ServedPatientListManager implements ServedPatientList {
    private static final Logger logger = LogsCenter.getLogger(PatientQueueManager.class);
    private ArrayList<ServedPatient> servedPatientList = new ArrayList<>();


    public ServedPatientListManager() {}

    /**
     * Create a new Patient Queue Manager with {@code patientListToCopy}
     */
    public ServedPatientListManager(List<ServedPatient> patientListToCopy) {
        this.servedPatientList = (ArrayList<ServedPatient>) patientListToCopy;
    }

    @Override
    public String displayServedPatientList() {
        StringBuilder sb = new StringBuilder();
        AtomicInteger num = new AtomicInteger(0);
        sb.append("Served Patients:");
        servedPatientList.forEach(x -> sb.append("\n").append(num.incrementAndGet())
                                         .append(". ").append(x.toNameAndIc()));
        return sb.toString();
    }

    @Override
    public int getServedPatientListLength() {
        return servedPatientList.size();
    }

    @Override
    public void addServedPatient(ServedPatient patient) {
        servedPatientList.add(patient);
    }

    @Override
    public ServedPatient removeAtIndex(int index) {
        ServedPatient servedPatient = servedPatientList.remove(index);
        servedPatient.getPatient().leaveQueue();
        return servedPatient;
    }

    @Override
    public void reset() {
        servedPatientList.clear();
    }

    @Override
    public ServedPatient selectServedPatient(Index index) {
        return servedPatientList.get(index.getZeroBased());
    }

    @Override
    public boolean isEmpty() {
        return servedPatientList.isEmpty();
    }

    @Override
    public boolean contains(ServedPatient patient) {
        return servedPatientList.contains(patient);
    }

    @Override
    public ArrayList<ServedPatient> getPatientsAsList() {
        return servedPatientList;
    }

    @Override
    public boolean containsPatient(Patient patient) {
        for (ServedPatient servedPatient : servedPatientList) {
            if (servedPatient.isPatient(patient)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return servedPatientList.size();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ServedPatientListManager)) {
            return false;
        }

        // state check
        ServedPatientListManager other = (ServedPatientListManager) obj;
        return this.servedPatientList.equals(other.servedPatientList);
    }
}
