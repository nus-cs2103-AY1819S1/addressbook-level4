package seedu.clinicio.commons.events.ui;

import seedu.clinicio.commons.events.BaseEvent;
import seedu.clinicio.model.person.Person;

/**
 * Represents a selection change in the Person List Panel
 */
public class PatientPanelSelectionChangedEvent extends BaseEvent {


    private final Person newSelection;

    public PatientPanelSelectionChangedEvent(Person newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getNewSelection() {
        return newSelection;
    }
}
