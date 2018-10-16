package seedu.address.model;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the current list of served patients during runtime.
 */
public class ServedPatientListManager implements ServedPatientList {
    private static final Logger logger = LogsCenter.getLogger(PatientQueueManager.class);
    private ArrayList<ServedPatient> servedPatientList = new ArrayList<>();

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
}
