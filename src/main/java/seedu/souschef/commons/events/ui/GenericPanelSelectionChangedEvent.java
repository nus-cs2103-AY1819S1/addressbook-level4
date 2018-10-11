package seedu.souschef.commons.events.ui;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents a selection change in the List Panel
 */
public class GenericPanelSelectionChangedEvent<T> extends BaseEvent {


    protected final T newSelection;

    public GenericPanelSelectionChangedEvent(T newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public T getNewSelection() {
        return newSelection;
    }
}
