package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag((String) null));
        Assert.assertThrows(NullPointerException.class, () -> new Tag((Tag) null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals() {
        Tag tagHusband = new Tag(VALID_TAG_HUSBAND);
        Tag tagFriend = new Tag(VALID_TAG_FRIEND);
        // null -> return false
        assertFalse(tagHusband.equals(null));

        // same object -> returns true
        assertTrue(tagHusband.equals(tagHusband));

        // different type -> returns false
        assertFalse(tagFriend.equals(99));

        // different tag -> returns false
        assertFalse(tagHusband.equals(tagFriend));
    }

}
