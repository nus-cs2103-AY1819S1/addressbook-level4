package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * A UI event class that is used to hide the {@code StaffPanel}
 */
public class HideStaffPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
