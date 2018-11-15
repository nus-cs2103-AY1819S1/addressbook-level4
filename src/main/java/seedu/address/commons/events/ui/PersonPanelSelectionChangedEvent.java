package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Patient;

/**
 * Represents a selection change in the Patient List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Patient newSelection;

    public PersonPanelSelectionChangedEvent(Patient newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Patient getNewSelection() {
        return newSelection;
    }
}
