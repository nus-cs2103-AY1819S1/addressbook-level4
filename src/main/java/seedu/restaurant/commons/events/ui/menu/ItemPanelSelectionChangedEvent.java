package seedu.restaurant.commons.events.ui.menu;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.menu.Item;

//@@author yican95
/**
 * Represents a selection change in the Item List Panel
 */
public class ItemPanelSelectionChangedEvent extends BaseEvent {

    private final Item newSelection;

    public ItemPanelSelectionChangedEvent(Item newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Item getNewSelection() {
        return newSelection;
    }
}
