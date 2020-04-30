package seedu.restaurant.commons.events.ui.menu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;

//@@author yican95
public class JumpToItemListRequestEventTest {

    @Test
    public void createEvent_success() {
        BaseEvent event = new JumpToItemListRequestEvent(Index.fromOneBased(1));
        assertEquals("JumpToItemListRequestEvent", event.toString());
    }
}
