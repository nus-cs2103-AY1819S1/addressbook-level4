package seedu.address.model.person.medicalrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MessageTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Message(null));
    }

    @Test
    public void constructor_invalidMessage_throwsIllegalArgumentException() {
        String invalidMessage = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Message(invalidMessage));
    }

    @Test
    public void isValidMessage() {
        // null Message
        Assert.assertThrows(NullPointerException.class, () -> Message.isValidMessage(null));

        // invalid Message
        assertFalse(Message.isValidMessage(""));
        assertFalse(Message.isValidMessage(" "));

        // valid Message
        assertTrue(Message.isValidMessage("This is a message"));
        assertTrue(Message.isValidMessage("This guy is sick"));
        assertTrue(Message.isValidMessage("Hi doctor"));


    }
}
