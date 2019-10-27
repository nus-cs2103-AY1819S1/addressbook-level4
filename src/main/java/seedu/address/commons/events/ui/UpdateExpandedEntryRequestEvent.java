package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.entry.ResumeEntry;

/**
 * a event indicating a need to update the ExpandedEntryPanel.
 */
public class UpdateExpandedEntryRequestEvent extends BaseEvent {
    private final ResumeEntry updatedEntry;

    public UpdateExpandedEntryRequestEvent(ResumeEntry updatedEntry) {
        this.updatedEntry = updatedEntry;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public ResumeEntry getUpdatedEntry() {
        return updatedEntry;
    }

}
