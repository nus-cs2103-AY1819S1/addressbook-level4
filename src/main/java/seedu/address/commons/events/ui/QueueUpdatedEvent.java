package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;

/**
 * Indicates that the queue has been updated.
 */
public class QueueUpdatedEvent extends BaseEvent {

    private PatientQueue patientQueue;
    private ServedPatientList servedPatientList;
    private CurrentPatient currentPatient;

    public QueueUpdatedEvent(PatientQueue patientQueue, ServedPatientList servedPatientList,
                             CurrentPatient currentPatient) {
        this.patientQueue = patientQueue;
        this.servedPatientList = servedPatientList;
        this.currentPatient = currentPatient;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public PatientQueue getPatientQueue() {
        return this.patientQueue;
    }

    public ServedPatientList getServedPatientList() {
        return this.servedPatientList;
    }

    public CurrentPatient getCurrentPatient() {
        return this.currentPatient;
    }

}
