package seedu.restaurant.commons.events.ui.reservation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;

//@@author m4dkip
public class DisplayReservationListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayReservationListRequestEvent();
        assertEquals("DisplayReservationListRequestEvent", event.toString());
    }
}
