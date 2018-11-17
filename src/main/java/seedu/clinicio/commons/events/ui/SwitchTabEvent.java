package seedu.clinicio.commons.events.ui;
//@@author iamjackslayer
import seedu.clinicio.commons.events.BaseEvent;

/**
 * Indicates the current displayed {@code Tab} is switched.
 */
public class SwitchTabEvent extends BaseEvent {

    private final int tabIndex;

    public SwitchTabEvent(int index) {
        this.tabIndex = index;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
