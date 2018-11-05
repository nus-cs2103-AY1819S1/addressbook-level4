package seedu.restaurant.commons.events.ui.reservation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.testutil.reservation.ReservationBuilder;

public class ReservationPanelSelectionChangedEventTest {

    private final Reservation reservation = new ReservationBuilder().withName("Ming Xian").withPax("2")
            .withDate("05-05-2020").withTime("10:00").withTags("Dinner").build();

    @Test
    public void createEvent_success() {
        BaseEvent event = new ReservationPanelSelectionChangedEvent(reservation);
        assertEquals("ReservationPanelSelectionChangedEvent", event.toString());
    }

    @Test
    public void createEvent_correctItem_success() {
        ReservationPanelSelectionChangedEvent event = new ReservationPanelSelectionChangedEvent(reservation);
        System.out.println(event.getNewSelection().toString());
        assertEquals("Name: Ming Xian Pax: 2 Date: 05-05-2020 Time: 10:00 Tags: [Dinner]",
                event.getNewSelection().toString());
    }
}
