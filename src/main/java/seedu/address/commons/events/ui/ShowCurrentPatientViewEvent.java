package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.CurrentPatient;

/**
 * An event representing request to show current patient's details.
 */
public class ShowCurrentPatientViewEvent extends BaseEvent {

    private CurrentPatient currentPatient;

    public ShowCurrentPatientViewEvent(CurrentPatient currentPatient) {
        this.currentPatient = currentPatient;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public CurrentPatient getCurrentPatient() {
        return this.currentPatient;
    }

}
