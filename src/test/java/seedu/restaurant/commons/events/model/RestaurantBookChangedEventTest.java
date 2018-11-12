package seedu.restaurant.commons.events.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.util.SampleDataUtil;

public class RestaurantBookChangedEventTest {
    @Test
    public void createEvent_withEmptyRestaurantBook_success() {
        BaseEvent event = new RestaurantBookChangedEvent(new RestaurantBook());
        assertEquals("number of accounts 0, number of items 0, number of reservations 0, "
                + "number of sales record 0, " + "number of ingredients 0", event.toString());
    }

    @Test
    public void createEvent_withDefaultRestaurantBook_success() {
        BaseEvent event = new RestaurantBookChangedEvent(SampleDataUtil.getDefaultRestaurantBook());
        assertEquals("number of accounts 1, number of items 0, number of reservations 0, "
                + "number of sales record 0, number of ingredients 0", event.toString());
    }

    @Test
    public void createEvent_withSampleRestaurantBook_success() {
        BaseEvent event = new RestaurantBookChangedEvent(SampleDataUtil.getSampleRestaurantBook());
        assertEquals("number of accounts 1, number of items 2, number of reservations 2, "
                + "number of sales record 6, number of ingredients 5", event.toString());
    }
}
