package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.BaseEvent;

//@@author chivent
/**
 * An event that notifies StatusBarFooter regarding a login status change.
 */
public class FilmReelSelectionChangeEvent extends BaseEvent {

    public final int index;

    /**
     * Constructor for LoginStatusEvent
     *
     * @param index index of image chosen
     */
    public FilmReelSelectionChangeEvent(int index) {

        requireNonNull(index);

        if (index >= 0) {
            this.index = index;
        } else {
            this.index = -1;
        }

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
