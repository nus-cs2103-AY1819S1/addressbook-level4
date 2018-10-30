package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.project.Assignment;

public class AssignmentPanelSelectionChangeEvent extends BaseEvent {

    private final Assignment newSelection;

    public AssignmentPanelSelectionChangeEvent(Assignment newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Assignment getNewSelection() {
        return newSelection;
    }
}
