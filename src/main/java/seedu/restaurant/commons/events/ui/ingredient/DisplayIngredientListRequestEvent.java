package seedu.restaurant.commons.events.ui.ingredient;

import seedu.restaurant.commons.events.BaseEvent;

/**
 * An event requesting to display IngredientListPanel.
 */
public class DisplayIngredientListRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
