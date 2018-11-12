package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ContextTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Context(null, null));
    }

    @Test
    public void constructor_invalidContextId_throwsIllegalArgumentException() {
        String invalidContextId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Context(invalidContextId, invalidContextId));
    }

    @Test
    public void isValidContext() {
        // null contextId
        Assert.assertThrows(NullPointerException.class, () -> Context.isValidContextId(null));

        // invalid contextId
        assertFalse(Context.isValidContextId("")); // empty string
        assertFalse(Context.isValidContextId(" ")); // spaces only

        // valid contextId
        assertTrue(Context.isValidContextId(Context.EVENT_CONTEXT_ID));
        assertTrue(Context.isValidContextId(Context.VOLUNTEER_CONTEXT_ID)); // one character
    }
}
