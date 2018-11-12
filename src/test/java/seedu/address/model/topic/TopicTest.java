package seedu.address.model.topic;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TopicTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Topic(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Topic(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null topic name
        Assert.assertThrows(NullPointerException.class, () -> Topic.isValidTopicName(null));
    }

}
