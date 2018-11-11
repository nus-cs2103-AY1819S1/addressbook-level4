package seedu.restaurant.commons.events.ui.ingredient;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;

//@@author rebstan97
/**
 * Indicates a request to jump to the list of ingredients
 */
public class JumpToIngredientListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToIngredientListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
