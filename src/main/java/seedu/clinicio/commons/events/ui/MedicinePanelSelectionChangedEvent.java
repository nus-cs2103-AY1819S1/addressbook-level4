package seedu.clinicio.commons.events.ui;

//@@author aaronseahyh

import seedu.clinicio.commons.events.BaseEvent;
import seedu.clinicio.model.medicine.Medicine;

/**
 * Represents a selection change in the Medicine List Panel.
 */
public class MedicinePanelSelectionChangedEvent extends BaseEvent {

    private final Medicine newSelection;

    public MedicinePanelSelectionChangedEvent(Medicine newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Medicine getNewSelection() {
        return newSelection;
    }

}
