package seedu.restaurant.commons.events.ui.menu;

import seedu.restaurant.commons.events.BaseEvent;

/**
 * An event requesting to display ItemListPanel.
 */
public class DisplayItemListRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
