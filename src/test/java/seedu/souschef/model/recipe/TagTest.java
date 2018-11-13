package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.souschef.testutil.Assert;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
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

        // invalid tag
        assertFalse(Tag.isValidTagName("")); // empty
        assertFalse(Tag.isValidTagName(" ")); // space-only
        assertFalse(Tag.isValidTagName("korean ")); // single-worded with space
        assertFalse(Tag.isValidTagName("fried food")); // words with space

        // valid tag
        assertTrue(Tag.isValidTagName("korean")); // single-worded
        assertTrue(Tag.isValidTagName("Korean")); // Capitalised single-worded
        assertTrue(Tag.isValidTagName("HALAL")); // block letters
    }

}
