package seedu.souschef.commons.events.model;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.model.ReadOnlyAppContent;

/** Indicates the AppContent has changed*/
public class AppContentChangedEvent extends BaseEvent {

    public final ReadOnlyAppContent data;

    public AppContentChangedEvent(ReadOnlyAppContent data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of recipes " + data.getObservableRecipeList().size();
    }
}
