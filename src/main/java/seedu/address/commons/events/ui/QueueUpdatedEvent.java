package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.PatientQueue;

/**
 * Indicates that the queue has been updated.
 */
public class QueueUpdatedEvent extends BaseEvent {

    private PatientQueue patientQueue;

    public QueueUpdatedEvent(PatientQueue patientQueue) {
        this.patientQueue = patientQueue;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public PatientQueue getPatientQueue() {
        return this.patientQueue;
    }

}
