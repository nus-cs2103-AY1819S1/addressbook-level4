package seedu.address.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToEntryListRequestEvent;
import seedu.address.commons.events.ui.UpdateExpandedEntryRequestEvent;

/**
 * a class to test all added classes extending from BaseEvent.
 */
public class TestForSelfAddedEvents {

    @Test
    public void jumpToEntryListEventTest() {
        JumpToEntryListRequestEvent e = new JumpToEntryListRequestEvent(Index.fromOneBased(1));
        assertTrue(e.toString().equals("JumpToEntryListRequestEvent"));
    }

    @Test
    public void updateExpandedEntryEventTest() {
        UpdateExpandedEntryRequestEvent e = new UpdateExpandedEntryRequestEvent(WORK_FACEBOOK);
        assertTrue(e.toString().equals("UpdateExpandedEntryRequestEvent"));
        assertEquals(e.getUpdatedEntry(), WORK_FACEBOOK);
    }


}
