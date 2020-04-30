package seedu.restaurant.commons.events.ui.reservation;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.reservation.Reservation;

//@@author m4dkip
/**
 * Represents a selection change in the Reservation List Panel
 */
public class ReservationPanelSelectionChangedEvent extends BaseEvent {


    private final Reservation newSelection;

    public ReservationPanelSelectionChangedEvent(Reservation newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Reservation getNewSelection() {
        return newSelection;
    }
}
