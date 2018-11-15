package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event representing request to show medicine list on the list panel.
 */
public class ShowMedicineListEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
