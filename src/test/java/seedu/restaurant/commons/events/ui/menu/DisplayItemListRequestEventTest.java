package seedu.restaurant.commons.events.ui.menu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;

//@@author yican95
public class DisplayItemListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayItemListRequestEvent();
        assertEquals("DisplayItemListRequestEvent", event.toString());
    }
}
