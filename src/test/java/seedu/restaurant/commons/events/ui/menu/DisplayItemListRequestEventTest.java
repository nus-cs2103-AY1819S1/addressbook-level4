package seedu.restaurant.commons.events.ui.menu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;

public class DisplayItemListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayItemListRequestEvent();
        assertEquals("DisplayItemListRequestEvent", event.toString());
    }
}
