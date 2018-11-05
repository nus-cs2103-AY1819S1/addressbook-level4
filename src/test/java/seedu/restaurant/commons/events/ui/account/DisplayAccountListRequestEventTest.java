package seedu.restaurant.commons.events.ui.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.commons.events.ui.accounts.DisplayAccountListRequestEvent;

public class DisplayAccountListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayAccountListRequestEvent();
        assertEquals("DisplayAccountListRequestEvent", event.toString());
    }
}
