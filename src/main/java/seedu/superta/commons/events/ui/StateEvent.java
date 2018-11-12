package seedu.superta.commons.events.ui;

import seedu.superta.commons.events.BaseEvent;
import seedu.superta.model.ReadOnlySuperTaClient;

/**
 * StateEvent refers to any event which can possibly alter the underlying model.
 * To update UI correctly.
 */
public class StateEvent extends BaseEvent {
    private ReadOnlySuperTaClient model;

    public StateEvent(ReadOnlySuperTaClient model) {
        this.model = model;
    }

    public ReadOnlySuperTaClient getModel() {
        return this.model;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
