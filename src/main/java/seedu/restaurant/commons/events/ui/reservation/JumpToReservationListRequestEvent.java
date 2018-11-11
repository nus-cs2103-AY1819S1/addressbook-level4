package seedu.restaurant.commons.events.ui.reservation;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;

//@@author m4dkip
/**
 * Indicates a request to jump to the list of reservations
 */
public class JumpToReservationListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToReservationListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
