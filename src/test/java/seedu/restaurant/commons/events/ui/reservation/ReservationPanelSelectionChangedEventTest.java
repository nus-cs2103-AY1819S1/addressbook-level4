package seedu.restaurant.commons.events.ui.reservation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.testutil.reservation.ReservationBuilder;

public class ReservationPanelSelectionChangedEventTest {

    //TODO: Ming Xian needs to update the datetime handling here to be more intuitive
    private final Reservation reservation = new ReservationBuilder().withName("Ming Xian").withPax("2")
            .withDateTime("2020-05-05T10:00:00").withTags("Dinner").build();

    @Test
    public void createEvent_success() {
        BaseEvent event = new ReservationPanelSelectionChangedEvent(reservation);
        assertEquals("ReservationPanelSelectionChangedEvent", event.toString());
    }

    @Test
    public void createEvent_correctItem_success() {
        ReservationPanelSelectionChangedEvent event = new ReservationPanelSelectionChangedEvent(reservation);
        System.out.println(event.getNewSelection().toString());
        assertEquals("Name: Ming Xian Pax: 2 Date & Time: 2020-05-05T10:00 Tags: [Dinner]",
                event.getNewSelection().toString());
    }
}
