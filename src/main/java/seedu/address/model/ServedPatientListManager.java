package seedu.address.model;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ServedPatient;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Represents the list of served patients during runtime.
 */
public class ServedPatientListManager implements ServedPatientList {
    private static final Logger logger = LogsCenter.getLogger(PatientQueueManager.class);
    private ArrayList<ServedPatient> servedPatientList = new ArrayList<>();

    @Override
    public String displayServedPatientList() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Served Patient: ");
        servedPatientList.stream()
                .forEach(x -> sb.append(x.toNameAndIc() + ", "));
        return sb.substring(0, sb.length() - 2);
    }

    @Override
    public void addServedPatient(ServedPatient patient) {
        servedPatientList.add(patient);
    }

    @Override
    public ServedPatient removeServedPatient(ServedPatient patient) {
        servedPatientList.remove(patient);
        return patient;
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
