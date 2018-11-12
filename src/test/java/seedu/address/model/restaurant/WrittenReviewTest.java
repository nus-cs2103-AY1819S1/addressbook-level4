package seedu.address.model.restaurant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class WrittenReviewTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new WrittenReview(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidReview = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new WrittenReview(invalidReview));
    }

    @Test
    public void isValidWrittenReview() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> WrittenReview.isValidWrittenReview(null));

        // invalid addresses
        assertFalse(WrittenReview.isValidWrittenReview("")); // empty string
        assertFalse(WrittenReview.isValidWrittenReview(" ")); // spaces only

        // valid addresses
        assertTrue(WrittenReview.isValidWrittenReview("Hello World. This restaurant is fantastic."));
        assertTrue(WrittenReview.isValidWrittenReview(".")); // one character
        assertTrue(WrittenReview.isValidWrittenReview("n1c3!!!!")); // Mixed Characters
    }
}
