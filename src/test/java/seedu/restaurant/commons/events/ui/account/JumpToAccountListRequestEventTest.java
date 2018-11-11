package seedu.restaurant.commons.events.ui.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.commons.events.ui.accounts.JumpToAccountListRequestEvent;

//@@author AZhiKai
public class JumpToAccountListRequestEventTest {

    @Test
    public void createEvent_success() {
        BaseEvent event = new JumpToAccountListRequestEvent(Index.fromOneBased(1));
        assertEquals("JumpToAccountListRequestEvent", event.toString());
    }
}
