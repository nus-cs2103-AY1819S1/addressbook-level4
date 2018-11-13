package seedu.souschef.commons.events.storage;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.logic.parser.Context;

/**
 * Event that is raised whenever StorageManager needs to switch main feature storage.
 * Contains the context of the main feature.
 */
public class SwitchFeatureStorageEvent extends BaseEvent {

    public final Context context;

    public SwitchFeatureStorageEvent(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + context.toString() + "]";
    }
}
