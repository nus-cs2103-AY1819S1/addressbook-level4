package seedu.restaurant.commons.events.ui.sales;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;

//@@author HyperionNKJ
public class DisplayRecordListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayRecordListRequestEvent();
        assertEquals("DisplayRecordListRequestEvent", event.toString());
    }
}
