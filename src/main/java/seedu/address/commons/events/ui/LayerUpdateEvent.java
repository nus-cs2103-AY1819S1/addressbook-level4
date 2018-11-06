package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author j-lum
/**
 * A simple event that notifies the layer list to refresh.
 */

public class LayerUpdateEvent extends BaseEvent {

    public final ArrayList<String> list;
    public final Index current;

    /**
     * Constructor for a LayerUpdateEvent
     * @param toChange
     */
    public LayerUpdateEvent(ArrayList<String> toChange, Index current) {
        this.list = requireNonNull(toChange);
        this.current = current;
        System.out.println(String.format("current is now ", current.getOneBased()));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
