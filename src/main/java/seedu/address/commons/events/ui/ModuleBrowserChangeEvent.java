package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.module.Module;

/**
 * Represents a selection change in the Module Browser Panel
 */
public class ModuleBrowserChangeEvent extends BaseEvent {

    private final Module currSelection;

    public ModuleBrowserChangeEvent(Module currSelection) {
        this.currSelection = currSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Module getCurrSelection() {
        return currSelection;
    }
}
