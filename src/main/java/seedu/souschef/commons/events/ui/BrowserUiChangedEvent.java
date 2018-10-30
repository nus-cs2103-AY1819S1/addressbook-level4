package seedu.souschef.commons.events.ui;

import seedu.souschef.commons.events.BaseEvent;

/**
 * event raised when there are changes to be made on the browser segment of the ui.
 */
public class BrowserUiChangedEvent extends BaseEvent {

    public final String type;
    public final int index;

    public BrowserUiChangedEvent(String type) {
        this.type = type;
        this.index = 0;
    }
    public BrowserUiChangedEvent(String type, int index) {
        this.index = index;
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }


}
