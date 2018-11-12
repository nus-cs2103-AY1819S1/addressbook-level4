package seedu.souschef.commons.events.ui;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.logic.parser.Context;

/**
 * Event that is raised whenever ListPanel changes context.
 */
public class ListPanelSwitchEvent extends BaseEvent {

    public final Context context;

    public ListPanelSwitchEvent(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + context.toString() + "]";
    }
}
